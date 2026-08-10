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

import java.util.Collections;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mitre.oauth2.model.RegisteredClient;
import org.mitre.openid.connect.client.SubjectIssuerGrantedAuthority;
import org.mitre.openid.connect.client.service.AuthRequestOptionsService;
import org.mitre.openid.connect.client.service.AuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.RegisteredClientService;
import org.mitre.openid.connect.client.service.impl.InMemoryRegisteredClientService;
import org.mitre.openid.connect.client.service.impl.PlainAuthRequestUrlBuilder;
import org.mitre.openid.connect.client.service.impl.StaticAuthRequestOptionsService;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.security.core.GrantedAuthority;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {{ @link MitreOpenIDAutoConfiguration }}.
 *
 * <p>Verifies the auto-configuration activates under the expected conditions
 * and exposes its declared beans.</p>
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@DisplayName("MitreOpenIDAutoConfiguration Tests")
class MitreOpenIDAutoConfigurationTest {

    private MitreOpenIDAutoConfiguration configuration;
    private MitreOpenIDProperties properties;

    @BeforeEach
    void setUp() throws Exception {
        configuration = new MitreOpenIDAutoConfiguration();
        properties = new MitreOpenIDProperties();

        // Set properties via reflection
        java.lang.reflect.Field propsField = MitreOpenIDAutoConfiguration.class.getDeclaredField("properties");
        propsField.setAccessible(true);
        propsField.set(configuration, properties);
    }

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        MitreOpenIDAutoConfiguration config = new MitreOpenIDAutoConfiguration();
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @ConditionalOnProperty annotation")
    void testConditionalOnPropertyAnnotation() {
        ConditionalOnProperty prop = MitreOpenIDAutoConfiguration.class.getAnnotation(ConditionalOnProperty.class);
        assertThat(prop).isNotNull();
        assertThat(prop.name()).containsExactly("mitreid.openid.enabled");
        assertThat(prop.havingValue()).isEqualTo("true");
    }

    @Test
    @DisplayName("Auto-configuration has @ConditionalOnClass annotation")
    void testConditionalOnClassAnnotation() {
        ConditionalOnClass clazz = MitreOpenIDAutoConfiguration.class.getAnnotation(ConditionalOnClass.class);
        assertThat(clazz).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @AutoConfigureAfter annotation")
    void testAutoConfigureAfterAnnotation() {
        AutoConfigureAfter after = MitreOpenIDAutoConfiguration.class.getAnnotation(AutoConfigureAfter.class);
        assertThat(after).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @EnableConfigurationProperties annotation")
    void testEnableConfigurationPropertiesAnnotation() {
        EnableConfigurationProperties props = MitreOpenIDAutoConfiguration.class.getAnnotation(EnableConfigurationProperties.class);
        assertThat(props).isNotNull();
        assertThat(props.value()).containsExactly(MitreOpenIDProperties.class);
    }

    @Test
    @DisplayName("Auto-configuration implements ApplicationContextAware")
    void testImplementsApplicationContextAware() {
        assertThat(ApplicationContextAware.class.isAssignableFrom(MitreOpenIDAutoConfiguration.class)).isTrue();
    }

    @Test
    @DisplayName("setApplicationContext and getApplicationContext work correctly")
    void testApplicationContextMethods() {
        MitreOpenIDAutoConfiguration config = new MitreOpenIDAutoConfiguration();
        ApplicationContext ctx = mock(ApplicationContext.class);
        config.setApplicationContext(ctx);
        assertThat(config.getApplicationContext()).isSameAs(ctx);
    }

    @Test
    @DisplayName("getApplicationContext returns null initially")
    void testGetApplicationContextInitiallyNull() {
        MitreOpenIDAutoConfiguration config = new MitreOpenIDAutoConfiguration();
        assertThat(config.getApplicationContext()).isNull();
    }

    @Test
    @DisplayName("authRequestOptions creates StaticAuthRequestOptionsService")
    void testAuthRequestOptions() {
        AuthRequestOptionsService service = configuration.authRequestOptions();
        assertThat(service).isInstanceOf(StaticAuthRequestOptionsService.class);
    }

    @Test
    @DisplayName("registeredClient creates client with authorities")
    void testRegisteredClient() {
        Set<GrantedAuthority> authorities = Collections.emptySet();
        RegisteredClient client = configuration.registeredClient(authorities);
        assertThat(client).isNotNull();
    }

    @Test
    @DisplayName("registeredClient creates client with null authorities")
    void testRegisteredClientWithNullAuthorities() {
        RegisteredClient client = configuration.registeredClient(null);
        assertThat(client).isNotNull();
    }

    @Test
    @DisplayName("registeredClientService creates InMemoryRegisteredClientService")
    void testRegisteredClientService() {
        RegisteredClientService service = configuration.registeredClientService();
        assertThat(service).isInstanceOf(InMemoryRegisteredClientService.class);
    }

    @Test
    @DisplayName("issuerGrantedAuthority creates SubjectIssuerGrantedAuthority")
    void testIssuerGrantedAuthority() {
        SubjectIssuerGrantedAuthority authority = configuration.issuerGrantedAuthority();
        assertThat(authority).isNotNull();
    }

    @Test
    @DisplayName("authRequestBuilder creates PlainAuthRequestUrlBuilder")
    void testAuthRequestBuilder() {
        AuthRequestUrlBuilder builder = configuration.authRequestBuilder();
        assertThat(builder).isInstanceOf(PlainAuthRequestUrlBuilder.class);
    }
}
