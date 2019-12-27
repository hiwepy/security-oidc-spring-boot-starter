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

@ConfigurationProperties(MitreOpenIDProperties.PREFIX)
public class MitreOpenIDProperties {

	public static final String PREFIX = "spring.security.mitre.openid";

	/** 是否启用 **/
	private boolean enabled = false;
	/** 需要写日志到数据库的包名 **/
	private String subject = "admin-subject";
	/** 需要写日志到数据库的包名 **/
	private String issuer = "https://admin-issuer.example.com/";
	private String accountChooserUrl;
	/**
	 * Name of the incoming parameter to check for discovery purposes.
	 */
	private String parameterName = "identifier";
	/**
	 * URL of the page to forward to if no identifier is given.
	 */
	private String loginPageUrl;
	/**
	 * Strict enfocement of "https"
	 */
	private boolean forceHttps = true;
	private Set<String> blacklist = new HashSet<String>();
	@NestedConfigurationProperty
	private RegisteredClient client = new RegisteredClient();
	@NestedConfigurationProperty
	private ServerConfiguration server = new ServerConfiguration();
	private Set<String> whitelist = new HashSet<String>();

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAccountChooserUrl() {
		return accountChooserUrl;
	}

	public void setAccountChooserUrl(String accountChooserUrl) {
		this.accountChooserUrl = accountChooserUrl;
	}

	public boolean isForceHttps() {
		return forceHttps;
	}

	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	public String getLoginPageUrl() {
		return loginPageUrl;
	}

	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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

	public RegisteredClient getClient() {
		return client;
	}

	public void setClient(RegisteredClient client) {
		this.client = client;
	}

	

}
