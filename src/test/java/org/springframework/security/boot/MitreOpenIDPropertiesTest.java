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

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.config.ServerConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {{ @link MitreOpenIDProperties }}.
 *
 * <p>Verifies default values, getters/setters and POJO contract.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("MitreOpenIDProperties Tests")
class MitreOpenIDPropertiesTest {

    @Test
    @DisplayName("Default constructor creates non-null instance")
    void testDefaultInstance() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props).isNotNull();
    }

    @Test
    @DisplayName("Field 'enabled' can be set and read")
    void testEnabledField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.isEnabled()).isFalse();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Field 'subject' can be set and read")
    void testSubjectField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getSubject()).isEqualTo("admin-subject");
        props.setSubject("custom-subject");
        assertThat(props.getSubject()).isEqualTo("custom-subject");
    }

    @Test
    @DisplayName("Field 'issuer' can be set and read")
    void testIssuerField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getIssuer()).isEqualTo("https://admin-issuer.example.com/");
        props.setIssuer("https://custom-issuer.example.com/");
        assertThat(props.getIssuer()).isEqualTo("https://custom-issuer.example.com/");
    }

    @Test
    @DisplayName("Field 'accountChooserUrl' can be set and read")
    void testAccountChooserUrlField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getAccountChooserUrl()).isNull();
        props.setAccountChooserUrl("https://chooser.example.com/");
        assertThat(props.getAccountChooserUrl()).isEqualTo("https://chooser.example.com/");
    }

    @Test
    @DisplayName("Field 'parameterName' can be set and read")
    void testParameterNameField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getParameterName()).isEqualTo("identifier");
        props.setParameterName("custom-param");
        assertThat(props.getParameterName()).isEqualTo("custom-param");
    }

    @Test
    @DisplayName("Field 'loginPageUrl' can be set and read")
    void testLoginPageUrlField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getLoginPageUrl()).isNull();
        props.setLoginPageUrl("https://login.example.com/");
        assertThat(props.getLoginPageUrl()).isEqualTo("https://login.example.com/");
    }

    @Test
    @DisplayName("Field 'forceHttps' can be set and read")
    void testForceHttpsField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.isForceHttps()).isTrue();
        props.setForceHttps(false);
        assertThat(props.isForceHttps()).isFalse();
    }

    @Test
    @DisplayName("Field 'blacklist' can be set and read")
    void testBlacklistField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getBlacklist()).isNotNull().isEmpty();
        Set<String> blacklist = new HashSet<>();
        blacklist.add("https://blocked.example.com");
        props.setBlacklist(blacklist);
        assertThat(props.getBlacklist()).containsExactly("https://blocked.example.com");
    }

    @Test
    @DisplayName("Field 'whitelist' can be set and read")
    void testWhitelistField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getWhitelist()).isNotNull().isEmpty();
        Set<String> whitelist = new HashSet<>();
        whitelist.add("https://example.com");
        props.setWhitelist(whitelist);
        assertThat(props.getWhitelist()).containsExactly("https://example.com");
    }

    @Test
    @DisplayName("Field 'client' can be set and read")
    void testClientField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getClient()).isNotNull();
        RegisteredClient client = new RegisteredClient();
        props.setClient(client);
        assertThat(props.getClient()).isSameAs(client);
    }

    @Test
    @DisplayName("Field 'server' can be set and read")
    void testServerField() {
        MitreOpenIDProperties props = new MitreOpenIDProperties();
        assertThat(props.getServer()).isNotNull();
        ServerConfiguration server = new ServerConfiguration();
        props.setServer(server);
        assertThat(props.getServer()).isSameAs(server);
    }

    @Test
    @DisplayName("Public constant 'PREFIX' has expected value")
    void testPREFIXConstant() {
        assertThat(MitreOpenIDProperties.PREFIX).isEqualTo("spring.security.mitre.openid");
    }
}
