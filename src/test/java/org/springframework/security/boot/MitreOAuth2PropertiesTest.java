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
 * Unit tests for {{ @link MitreOAuth2Properties }}.
 *
 * <p>Verifies default values, getters/setters and POJO contract.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("MitreOAuth2Properties Tests")
class MitreOAuth2PropertiesTest {

    @Test
    @DisplayName("Default constructor creates non-null instance")
    void testDefaultInstance() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props).isNotNull();
    }

    @Test
    @DisplayName("Field 'enabled' can be set and read")
    void testEnabledField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.isEnabled()).isFalse();
        props.setEnabled(true);
        assertThat(props.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Field 'defaultExpireTime' can be set and read")
    void testDefaultExpireTimeField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getDefaultExpireTime()).isEqualTo(300000);
        props.setDefaultExpireTime(60000);
        assertThat(props.getDefaultExpireTime()).isEqualTo(60000);
    }

    @Test
    @DisplayName("Field 'forceCacheExpireTime' can be set and read")
    void testForceCacheExpireTimeField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.isForceCacheExpireTime()).isFalse();
        props.setForceCacheExpireTime(true);
        assertThat(props.isForceCacheExpireTime()).isTrue();
    }

    @Test
    @DisplayName("Field 'cacheNonExpiringTokens' can be set and read")
    void testCacheNonExpiringTokensField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.isCacheNonExpiringTokens()).isFalse();
        props.setCacheNonExpiringTokens(true);
        assertThat(props.isCacheNonExpiringTokens()).isTrue();
    }

    @Test
    @DisplayName("Field 'cacheTokens' can be set and read")
    void testCacheTokensField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.isCacheTokens()).isTrue();
        props.setCacheTokens(false);
        assertThat(props.isCacheTokens()).isFalse();
    }

    @Test
    @DisplayName("Field 'issuer' can be set and read")
    void testIssuerField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getIssuer()).isEqualTo("https://admin-issuer.example.com/");
        props.setIssuer("https://custom-issuer.example.com/");
        assertThat(props.getIssuer()).isEqualTo("https://custom-issuer.example.com/");
    }

    @Test
    @DisplayName("Field 'jwtToken' can be set and read")
    void testJwtTokenField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.isJwtToken()).isTrue();
        props.setJwtToken(false);
        assertThat(props.isJwtToken()).isFalse();
    }

    @Test
    @DisplayName("Field 'introspectionUrl' can be set and read")
    void testIntrospectionUrlField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getIntrospectionUrl()).isNull();
        props.setIntrospectionUrl("https://introspect.example.com/");
        assertThat(props.getIntrospectionUrl()).isEqualTo("https://introspect.example.com/");
    }

    @Test
    @DisplayName("Field 'whitelist' can be set and read")
    void testWhitelistField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getWhitelist()).isNotNull().isEmpty();
        Set<String> whitelist = new HashSet<>();
        whitelist.add("https://example.com");
        props.setWhitelist(whitelist);
        assertThat(props.getWhitelist()).containsExactly("https://example.com");
    }

    @Test
    @DisplayName("Field 'blacklist' can be set and read")
    void testBlacklistField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getBlacklist()).isNotNull().isEmpty();
        Set<String> blacklist = new HashSet<>();
        blacklist.add("https://blocked.example.com");
        props.setBlacklist(blacklist);
        assertThat(props.getBlacklist()).containsExactly("https://blocked.example.com");
    }

    @Test
    @DisplayName("Field 'client' can be set and read")
    void testClientField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getClient()).isNotNull();
        RegisteredClient client = new RegisteredClient();
        props.setClient(client);
        assertThat(props.getClient()).isSameAs(client);
    }

    @Test
    @DisplayName("Field 'server' can be set and read")
    void testServerField() {
        MitreOAuth2Properties props = new MitreOAuth2Properties();
        assertThat(props.getServer()).isNotNull();
        ServerConfiguration server = new ServerConfiguration();
        props.setServer(server);
        assertThat(props.getServer()).isSameAs(server);
    }

    @Test
    @DisplayName("Public constant 'PREFIX' has expected value")
    void testPREFIXConstant() {
        assertThat(MitreOAuth2Properties.PREFIX).isEqualTo("spring.security.mitre.oauth2");
    }
}
