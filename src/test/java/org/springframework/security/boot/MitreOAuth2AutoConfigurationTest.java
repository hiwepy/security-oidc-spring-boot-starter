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
import org.mitre.oauth2.introspectingfilter.service.IntrospectionAuthorityGranter;
import org.mitre.oauth2.introspectingfilter.service.impl.SimpleIntrospectionAuthorityGranter;
import org.mitre.oauth2.model.RegisteredClient;
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
 * Unit tests for {{ @link MitreOAuth2AutoConfiguration }}.
 *
 * <p>Verifies the auto-configuration activates under the expected conditions
 * and exposes its declared beans.</p>
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@DisplayName("MitreOAuth2AutoConfiguration Tests")
class MitreOAuth2AutoConfigurationTest {

    private MitreOAuth2AutoConfiguration configuration;
    private MitreOAuth2Properties properties;

    @BeforeEach
    void setUp() throws Exception {
        configuration = new MitreOAuth2AutoConfiguration();
        properties = new MitreOAuth2Properties();

        // Set properties via reflection
        java.lang.reflect.Field propsField = MitreOAuth2AutoConfiguration.class.getDeclaredField("properties");
        propsField.setAccessible(true);
        propsField.set(configuration, properties);
    }

    @Test
    @DisplayName("Auto-configuration class can be instantiated")
    void testInstantiation() {
        MitreOAuth2AutoConfiguration config = new MitreOAuth2AutoConfiguration();
        assertThat(config).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @ConditionalOnProperty annotation")
    void testConditionalOnPropertyAnnotation() {
        ConditionalOnProperty prop = MitreOAuth2AutoConfiguration.class.getAnnotation(ConditionalOnProperty.class);
        assertThat(prop).isNotNull();
        assertThat(prop.name()).containsExactly("mitre.oauth2.enabled");
        assertThat(prop.havingValue()).isEqualTo("true");
    }

    @Test
    @DisplayName("Auto-configuration has @ConditionalOnClass annotation")
    void testConditionalOnClassAnnotation() {
        ConditionalOnClass clazz = MitreOAuth2AutoConfiguration.class.getAnnotation(ConditionalOnClass.class);
        assertThat(clazz).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @AutoConfigureAfter annotation")
    void testAutoConfigureAfterAnnotation() {
        AutoConfigureAfter after = MitreOAuth2AutoConfiguration.class.getAnnotation(AutoConfigureAfter.class);
        assertThat(after).isNotNull();
    }

    @Test
    @DisplayName("Auto-configuration has @EnableConfigurationProperties annotation")
    void testEnableConfigurationPropertiesAnnotation() {
        EnableConfigurationProperties props = MitreOAuth2AutoConfiguration.class.getAnnotation(EnableConfigurationProperties.class);
        assertThat(props).isNotNull();
        assertThat(props.value()).containsExactly(MitreOAuth2Properties.class);
    }

    @Test
    @DisplayName("Auto-configuration implements ApplicationContextAware")
    void testImplementsApplicationContextAware() {
        assertThat(ApplicationContextAware.class.isAssignableFrom(MitreOAuth2AutoConfiguration.class)).isTrue();
    }

    @Test
    @DisplayName("setApplicationContext and getApplicationContext work correctly")
    void testApplicationContextMethods() {
        MitreOAuth2AutoConfiguration config = new MitreOAuth2AutoConfiguration();
        ApplicationContext ctx = mock(ApplicationContext.class);
        config.setApplicationContext(ctx);
        assertThat(config.getApplicationContext()).isSameAs(ctx);
    }

    @Test
    @DisplayName("getApplicationContext returns null initially")
    void testGetApplicationContextInitiallyNull() {
        MitreOAuth2AutoConfiguration config = new MitreOAuth2AutoConfiguration();
        assertThat(config.getApplicationContext()).isNull();
    }

    @Test
    @DisplayName("introspectionAuthorityGranter returns SimpleIntrospectionAuthorityGranter")
    void testIntrospectionAuthorityGranter() {
        IntrospectionAuthorityGranter granter = configuration.introspectionAuthorityGranter();
        assertThat(granter).isInstanceOf(SimpleIntrospectionAuthorityGranter.class);
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
}
