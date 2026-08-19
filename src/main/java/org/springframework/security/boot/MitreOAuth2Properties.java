/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.springframework.security.boot;

import java.util.HashSet;
import java.util.Set;

import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.config.ServerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * <p>Configuration properties.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@ConfigurationProperties(MitreOAuth2Properties.PREFIX)
public class MitreOAuth2Properties {

	public static final String PREFIX = "spring.security.mitre.oauth2";
 
	/** 是否启用 **/
	private boolean enabled = false;
	private int defaultExpireTime = 300000; // 5 minutes in milliseconds
	private boolean forceCacheExpireTime = false; // force removal of cached tokens based on default expire time
	private boolean cacheNonExpiringTokens = false;
	private boolean cacheTokens = true;
	private String issuer = "https://admin-issuer.example.com/";
	private boolean jwtToken = true;
	private String introspectionUrl;
	@NestedConfigurationProperty
	private RegisteredClient client = new RegisteredClient();

	private Set<String> whitelist = new HashSet<String>();
	private Set<String> blacklist = new HashSet<String>();
	@NestedConfigurationProperty
	private ServerConfiguration server = new ServerConfiguration();
	
	/**
	 * Returns the issuer.
	 *
	 * @return the issuer
	 */
	public String getIssuer() {
		return issuer;
	}

	/**
	 * Sets the issuer.
	 *
	 * @param issuer the issuer
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Sets the enabled.
	 *
	 * @param enabled the enabled
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns the default expire time.
	 *
	 * @return the default expire time
	 */
	public int getDefaultExpireTime() {
		return defaultExpireTime;
	}

	/**
	 * Sets the default expire time.
	 *
	 * @param defaultExpireTime the default expire time
	 */
	public void setDefaultExpireTime(int defaultExpireTime) {
		this.defaultExpireTime = defaultExpireTime;
	}

	/**
	 * Returns the force cache expire time.
	 *
	 * @return the force cache expire time
	 */
	public boolean isForceCacheExpireTime() {
		return forceCacheExpireTime;
	}

	/**
	 * Sets the force cache expire time.
	 *
	 * @param forceCacheExpireTime the force cache expire time
	 */
	public void setForceCacheExpireTime(boolean forceCacheExpireTime) {
		this.forceCacheExpireTime = forceCacheExpireTime;
	}

	/**
	 * Returns the cache non expiring tokens.
	 *
	 * @return the cache non expiring tokens
	 */
	public boolean isCacheNonExpiringTokens() {
		return cacheNonExpiringTokens;
	}

	/**
	 * Sets the cache non expiring tokens.
	 *
	 * @param cacheNonExpiringTokens the cache non expiring tokens
	 */
	public void setCacheNonExpiringTokens(boolean cacheNonExpiringTokens) {
		this.cacheNonExpiringTokens = cacheNonExpiringTokens;
	}

	/**
	 * Returns the cache tokens.
	 *
	 * @return the cache tokens
	 */
	public boolean isCacheTokens() {
		return cacheTokens;
	}

	/**
	 * Sets the cache tokens.
	 *
	 * @param cacheTokens the cache tokens
	 */
	public void setCacheTokens(boolean cacheTokens) {
		this.cacheTokens = cacheTokens;
	}

	/**
	 * Returns the jwt token.
	 *
	 * @return the jwt token
	 */
	public boolean isJwtToken() {
		return jwtToken;
	}

	/**
	 * Sets the jwt token.
	 *
	 * @param jwtToken the jwt token
	 */
	public void setJwtToken(boolean jwtToken) {
		this.jwtToken = jwtToken;
	}

	/**
	 * Returns the introspection url.
	 *
	 * @return the introspection url
	 */
	public String getIntrospectionUrl() {
		return introspectionUrl;
	}

	/**
	 * Sets the introspection url.
	 *
	 * @param introspectionUrl the introspection url
	 */
	public void setIntrospectionUrl(String introspectionUrl) {
		this.introspectionUrl = introspectionUrl;
	}

	/**
	 * Returns the client.
	 *
	 * @return the client
	 */
	public RegisteredClient getClient() {
		return client;
	}

	/**
	 * Sets the client.
	 *
	 * @param client the client
	 */
	public void setClient(RegisteredClient client) {
		this.client = client;
	}

	/**
	 * Returns the whitelist.
	 *
	 * @return the whitelist
	 */
	public Set<String> getWhitelist() {
		return whitelist;
	}

	/**
	 * Sets the whitelist.
	 *
	 * @param whitelist the whitelist
	 */
	public void setWhitelist(Set<String> whitelist) {
		this.whitelist = whitelist;
	}

	/**
	 * Returns the blacklist.
	 *
	 * @return the blacklist
	 */
	public Set<String> getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist the blacklist
	 */
	public void setBlacklist(Set<String> blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Returns the server.
	 *
	 * @return the server
	 */
	public ServerConfiguration getServer() {
		return server;
	}

	/**
	 * Sets the server.
	 *
	 * @param server the server
	 */
	public void setServer(ServerConfiguration server) {
		this.server = server;
	}

	
	
}
