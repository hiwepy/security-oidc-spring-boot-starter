package org.springframework.security.boot;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.client.NamedAdminAuthoritiesMapper;
import org.mitre.openid.connect.client.OIDCAuthenticationFilter;
import org.mitre.openid.connect.client.OIDCAuthenticationProvider;
import org.mitre.openid.connect.client.OIDCAuthoritiesMapper;
import org.mitre.openid.connect.client.SubjectIssuerGrantedAuthority;
import org.mitre.openid.connect.client.service.AuthRequestOptionsService;
import org.mitre.openid.connect.client.service.AuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.ClientConfigurationService;
import org.mitre.openid.connect.client.service.IssuerService;
import org.mitre.openid.connect.client.service.RegisteredClientService;
import org.mitre.openid.connect.client.service.ServerConfigurationService;
import org.mitre.openid.connect.client.service.impl.HybridClientConfigurationService;
import org.mitre.openid.connect.client.service.impl.HybridIssuerService;
import org.mitre.openid.connect.client.service.impl.HybridServerConfigurationService;
import org.mitre.openid.connect.client.service.impl.InMemoryRegisteredClientService;
import org.mitre.openid.connect.client.service.impl.PlainAuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.impl.StaticAuthRequestOptionsService;
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
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.ObjectUtils;


/**
 * TODO
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * https://github.com/mitreid-connect/OpenID-Connect-Java-Spring-Server/wiki/Client-configuration
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ ServerConfiguration.class, ServerConfigurationService.class })
@ConditionalOnProperty(name = "mitreid.openid.enabled", havingValue = "true")
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@EnableConfigurationProperties(MitreOpenIDProperties.class)
public class MitreOpenIDAutoConfiguration implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Autowired
	private MitreOpenIDProperties properties;
	
	/**
	 * Returns the suer service.
	 *
	 * @return the suer service
	 */
	@Bean
	@ConditionalOnMissingBean
	public IssuerService issuerService() {
		
		HybridIssuerService issuerService = new HybridIssuerService();
		
		issuerService.setAccountChooserUrl(properties.getAccountChooserUrl());
		issuerService.setBlacklist(properties.getBlacklist());
		issuerService.setForceHttps(properties.isForceHttps());
		issuerService.setLoginPageUrl(properties.getLoginPageUrl());
		issuerService.setParameterName(properties.getParameterName());
		issuerService.setWhitelist(properties.getWhitelist());
		
		return issuerService;
	}
	
	/**
	 * auth Request Options.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthRequestOptionsService authRequestOptions() {
		return new StaticAuthRequestOptionsService();
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
	 * registered Client Service.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public RegisteredClientService registeredClientService() {
		return new InMemoryRegisteredClientService();
	}
	
	/**
	 * client Configuration.
	 *
	 * @param registeredClientService the registered client service
	 * @param registeredClient the registered client
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public ClientConfigurationService clientConfiguration(RegisteredClientService registeredClientService, 
			RegisteredClient registeredClient) {
		
		HybridClientConfigurationService configurationService = new HybridClientConfigurationService();
		
		configurationService.setBlacklist(properties.getBlacklist());
		Map<String, RegisteredClient> clients = new LinkedHashMap<String, RegisteredClient>();
		Map<String, RegisteredClient> beansOfType = getApplicationContext().getBeansOfType(RegisteredClient.class);
		if (! ObjectUtils.isEmpty(beansOfType)) {
			clients.putAll(beansOfType);
		}
		clients.put(properties.getIssuer(), registeredClient);
		
		configurationService.setClients(clients);
		configurationService.setRegisteredClientService(registeredClientService);
		configurationService.setTemplate(registeredClient);
		configurationService.setWhitelist(properties.getWhitelist());
		
		return configurationService;
	}

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
		ServerConfiguration configuration = properties.getServer();
		servers.put(properties.getIssuer(), configuration);
		configurationService.setServers(servers);
		return configurationService;
	}
	

	/**
	 * Returns the suer granted authority.
	 *
	 * @return the suer granted authority
	 */
	@Bean
	@ConditionalOnMissingBean
	public SubjectIssuerGrantedAuthority issuerGrantedAuthority() {
		return new SubjectIssuerGrantedAuthority(properties.getSubject(), properties.getIssuer());
	}
	
	/**
	 * authorities Mapper.
	 *
	 * @param admins the admins
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public OIDCAuthoritiesMapper authoritiesMapper(Set<SubjectIssuerGrantedAuthority> admins) {
		NamedAdminAuthoritiesMapper authoritiesMapper = new NamedAdminAuthoritiesMapper();
		authoritiesMapper.setAdmins(admins);
		return authoritiesMapper;
	}
	
	/**
	 * open ID Connect Authentication Provider.
	 *
	 * @param authoritiesMapper the authorities mapper
	 * @return the result
	 */
	@Bean
	public OIDCAuthenticationProvider openIdConnectAuthenticationProvider(OIDCAuthoritiesMapper authoritiesMapper) {
		OIDCAuthenticationProvider authcProvider = new OIDCAuthenticationProvider();
		authcProvider.setAuthoritiesMapper(authoritiesMapper);
		return authcProvider;
	}
	
	/**
	 * auth Request Builder.
	 *
	 * @return the result
	 */
	@Bean
	@ConditionalOnMissingBean
	public AuthRequestUrlBuilder authRequestBuilder() {
		return new PlainAuthRequestUrlBuilder();
	}
	
	/**
	 * open ID Connect Authentication Filter.
	 *
	 * @param authenticationManager the authentication manager
	 * @param issuerService the issuer service
	 * @param serverConfiguration the server configuration
	 * @param clientConfiguration the client configuration
	 * @param authOptions the auth options
	 * @param authRequestBuilder the auth request builder
	 * @return the result
	 */
	@Bean
	public OIDCAuthenticationFilter openIdConnectAuthenticationFilter(AuthenticationManager authenticationManager,
			IssuerService issuerService, ServerConfigurationService serverConfiguration,
			ClientConfigurationService clientConfiguration, AuthRequestOptionsService authOptions,
			AuthRequestUrlBuilder authRequestBuilder) {
		
		OIDCAuthenticationFilter filter = new OIDCAuthenticationFilter();
		
		filter.setAuthenticationManager(authenticationManager);
		//determines the issuer URL for the server
		filter.setIssuerService(issuerService);
		//determines the endpoint URLs and other attributes of the server
		filter.setServerConfigurationService(serverConfiguration);
		//determines the client identifier and credentials to use when talking to the server
		filter.setClientConfigurationService(clientConfiguration);
		//determines the extra options to add to the auth request
		filter.setAuthRequestOptionsService(authOptions);
		//builds the redirect URL to the auth request endpoint
		filter.setAuthRequestUrlBuilder(authRequestBuilder);
		
		return filter;
	}
	
	@Configuration
	@ConditionalOnWebApplication
	@ComponentScan("org.mitre.openid")
	@Order(-94)
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
