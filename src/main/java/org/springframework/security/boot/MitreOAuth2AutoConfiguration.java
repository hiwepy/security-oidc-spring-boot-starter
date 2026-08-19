package org.springframework.security.boot;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.http.client.HttpClient;
import org.mitre.oauth2.introspectingfilter.IntrospectingTokenService;
import org.mitre.oauth2.introspectingfilter.service.IntrospectionAuthorityGranter;
import org.mitre.oauth2.introspectingfilter.service.IntrospectionConfigurationService;
import org.mitre.oauth2.introspectingfilter.service.impl.JWTParsingIntrospectionConfigurationService;
import org.mitre.oauth2.introspectingfilter.service.impl.SimpleIntrospectionAuthorityGranter;
import org.mitre.oauth2.introspectingfilter.service.impl.StaticIntrospectionConfigurationService;
import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.client.service.ClientConfigurationService;
import org.mitre.openid.connect.client.service.ServerConfigurationService;
import org.mitre.openid.connect.client.service.impl.HybridServerConfigurationService;
import org.mitre.openid.connect.config.ServerConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.webmvc.autoconfigure.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;

/**
 * TODO
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * https://github.com/mitreid-connect/OpenID-Connect-Java-Spring-Server/wiki/Token-Introspecting-Client-Config
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ RegisteredClient.class, IntrospectingTokenService.class })
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(MitreOAuth2Properties.class)
@ConditionalOnProperty(name = "mitre.oauth2.enabled", havingValue = "true")
public class MitreOAuth2AutoConfiguration implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Autowired
	private MitreOAuth2Properties properties;
	@Autowired(required=false)
	private HttpClient httpClient;
	
	/**
	 * server Configuration.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public ServerConfigurationService serverConfiguration() {
		
		HybridServerConfigurationService configurationService = new HybridServerConfigurationService();
		configurationService.setBlacklist(properties.getBlacklist());
		configurationService.setWhitelist(properties.getWhitelist());
		
		Map<String, ServerConfiguration> servers = new LinkedHashMap<String, ServerConfiguration>();
		Map<String, ServerConfiguration> beansOfType = getApplicationContext().getBeansOfType(ServerConfiguration.class);
		if (! ObjectUtils.isEmpty(beansOfType)) {
			Iterator<Entry<String, ServerConfiguration>> ite = beansOfType.entrySet().iterator();
			while (ite.hasNext()) {
				ServerConfiguration configuration = ite.next().getValue();
				servers.put(configuration.getIssuer(), configuration);
			}
		}
		servers.put(properties.getIssuer(), properties.getServer());
		configurationService.setServers(servers);
		return configurationService;
	}
	
	
	/**
	 * introspection Authority Granter.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public IntrospectionAuthorityGranter introspectionAuthorityGranter() {
		return new SimpleIntrospectionAuthorityGranter();
	}
	
	/**
	 * registered Client.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public RegisteredClient registeredClient(@Autowired(required=false) Set<GrantedAuthority> authorities) {
		RegisteredClient registeredClient = properties.getClient();
		registeredClient.setAuthorities(authorities);
		return registeredClient;
	}
	
	/**
	 * introspection URL Provider.
	 *
	 * @param registeredClient the registered client
	 * @param serverConfiguration the server configuration
	 * @param clientConfiguration the client configuration
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public IntrospectionConfigurationService introspectionUrlProvider(RegisteredClient registeredClient,
			ServerConfigurationService serverConfiguration, ClientConfigurationService clientConfiguration) {
		
		if(properties.isJwtToken()) {
			
			JWTParsingIntrospectionConfigurationService jwtService =  new JWTParsingIntrospectionConfigurationService();
			jwtService.setClientConfigurationService(clientConfiguration);
			jwtService.setServerConfigurationService(serverConfiguration);
			
			return jwtService;
		}
		
		StaticIntrospectionConfigurationService staticService =  new StaticIntrospectionConfigurationService();
		staticService.setClientConfiguration(registeredClient);
		staticService.setIntrospectionUrl(properties.getIntrospectionUrl());
		
		return staticService;
		
	}
	
	/**
	 * introspecting Token Service.
	 *
	 * @param introspectionAuthorityGranter the introspection authority granter
	 * @param introspectionUrlProvider the introspection url provider
	 * @return the result
	 */
	@Bean
	public IntrospectingTokenService introspectingTokenService(IntrospectionAuthorityGranter introspectionAuthorityGranter, 
			IntrospectionConfigurationService introspectionUrlProvider) {
		
		IntrospectingTokenService tokenService = httpClient != null ? new IntrospectingTokenService(httpClient): new IntrospectingTokenService();
		
		tokenService.setCacheNonExpiringTokens(properties.isCacheNonExpiringTokens());
		tokenService.setCacheTokens(properties.isCacheTokens());
		tokenService.setDefaultExpireTime(properties.getDefaultExpireTime());
		tokenService.setForceCacheExpireTime(properties.isForceCacheExpireTime());
		tokenService.setIntrospectionAuthorityGranter(introspectionAuthorityGranter);
		tokenService.setIntrospectionConfigurationService(introspectionUrlProvider);
		
		return tokenService;
	}
	 
	@Configuration
	@ConditionalOnWebApplication
	@ComponentScan("org.mitre.oauth2")
	public static class MitreOauth2Configuration {
		
	}
	
	/**
	 * Sets the application context.
	 *
	 * @param applicationContext the application context
	 * @throws BeansException if an error occurs
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	/**
	 * Returns the application context.
	 *
	 * @return the application context
	 */
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
}
