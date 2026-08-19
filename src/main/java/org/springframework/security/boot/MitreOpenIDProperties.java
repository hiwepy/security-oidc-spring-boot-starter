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
	 * Returns the subject.
	 *
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * Sets the subject.
	 *
	 * @param subject the subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

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
	 * Returns the account chooser url.
	 *
	 * @return the account chooser url
	 */
	public String getAccountChooserUrl() {
		return accountChooserUrl;
	}

	/**
	 * Sets the account chooser url.
	 *
	 * @param accountChooserUrl the account chooser url
	 */
	public void setAccountChooserUrl(String accountChooserUrl) {
		this.accountChooserUrl = accountChooserUrl;
	}

	/**
	 * Returns the force https.
	 *
	 * @return the force https
	 */
	public boolean isForceHttps() {
		return forceHttps;
	}

	/**
	 * Sets the force https.
	 *
	 * @param forceHttps the force https
	 */
	public void setForceHttps(boolean forceHttps) {
		this.forceHttps = forceHttps;
	}

	/**
	 * Returns the login page url.
	 *
	 * @return the login page url
	 */
	public String getLoginPageUrl() {
		return loginPageUrl;
	}

	/**
	 * Sets the login page url.
	 *
	 * @param loginPageUrl the login page url
	 */
	public void setLoginPageUrl(String loginPageUrl) {
		this.loginPageUrl = loginPageUrl;
	}

	/**
	 * Returns the parameter name.
	 *
	 * @return the parameter name
	 */
	public String getParameterName() {
		return parameterName;
	}

	/**
	 * Sets the parameter name.
	 *
	 * @param parameterName the parameter name
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
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

	

}
