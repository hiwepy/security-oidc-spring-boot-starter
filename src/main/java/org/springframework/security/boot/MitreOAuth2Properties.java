/*
 * Copyright (c) 2017, vindell (https://github.com/vindell).
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
	
	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getDefaultExpireTime() {
		return defaultExpireTime;
	}

	public void setDefaultExpireTime(int defaultExpireTime) {
		this.defaultExpireTime = defaultExpireTime;
	}

	public boolean isForceCacheExpireTime() {
		return forceCacheExpireTime;
	}

	public void setForceCacheExpireTime(boolean forceCacheExpireTime) {
		this.forceCacheExpireTime = forceCacheExpireTime;
	}

	public boolean isCacheNonExpiringTokens() {
		return cacheNonExpiringTokens;
	}

	public void setCacheNonExpiringTokens(boolean cacheNonExpiringTokens) {
		this.cacheNonExpiringTokens = cacheNonExpiringTokens;
	}

	public boolean isCacheTokens() {
		return cacheTokens;
	}

	public void setCacheTokens(boolean cacheTokens) {
		this.cacheTokens = cacheTokens;
	}

	public boolean isJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(boolean jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getIntrospectionUrl() {
		return introspectionUrl;
	}

	public void setIntrospectionUrl(String introspectionUrl) {
		this.introspectionUrl = introspectionUrl;
	}

	public RegisteredClient getClient() {
		return client;
	}

	public void setClient(RegisteredClient client) {
		this.client = client;
	}

	public Set<String> getWhitelist() {
		return whitelist;
	}

	public void setWhitelist(Set<String> whitelist) {
		this.whitelist = whitelist;
	}

	public Set<String> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(Set<String> blacklist) {
		this.blacklist = blacklist;
	}

	public ServerConfiguration getServer() {
		return server;
	}

	public void setServer(ServerConfiguration server) {
		this.server = server;
	}

	
	
}
