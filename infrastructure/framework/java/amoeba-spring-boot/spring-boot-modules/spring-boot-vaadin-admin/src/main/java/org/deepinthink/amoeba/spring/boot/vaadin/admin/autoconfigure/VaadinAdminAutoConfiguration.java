/*
 * Copyright 2026-present DeepInThink. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.deepinthink.amoeba.spring.boot.vaadin.admin.autoconfigure;

import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import java.util.List;
import org.deepinthink.amoeba.spring.boot.vaadin.EnableVaadin;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.views.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;

@AutoConfiguration
@EnableVaadin(VaadinAdminProperties.PREFIX_VIEWS)
@EnableConfigurationProperties(VaadinAdminProperties.class)
public class VaadinAdminAutoConfiguration {

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public VaadinAdminLoginView vaadinAdminLoginView(VaadinAdminProperties properties) {
    return new VaadinAdminLoginView(properties.getLogin());
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public VaadinAdminMainView vaadinAdminMainView() {
    return new VaadinAdminMainView();
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public VaadinAdminHeaderView vaadinAdminHeaderView(
      AuthenticationContext context, VaadinAdminProperties properties) {
    return new VaadinAdminHeaderView(context, properties.getHeader());
  }

  @Bean
  @Scope("prototype")
  @ConditionalOnMissingBean
  public VaadinAdminMainLayout vaadinAdminMainLayout(
      AuthenticationContext context,
      VaadinAdminHeaderView header,
      ObjectProvider<VaadinAdminSideNavItemSupplier> provider) {
    return new VaadinAdminMainLayout(context, header, provider);
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass({HttpSecurity.class, VaadinSecurityConfigurer.class})
  public static class VaadinAdminSecurityConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
      return http.with(
              VaadinSecurityConfigurer.vaadin(), c -> c.loginView(VaadinAdminLoginView.class))
          .build();
    }

    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean
    public UserDetailsService userDetailsService(
        VaadinAdminProperties adminProperties, PasswordEncoder passwordEncoder) {
      VaadinAdminSecurityProperties properties = adminProperties.getSecurity();
      if (CollectionUtils.isEmpty(properties.getUsers())) {
        return new InMemoryUserDetailsManager();
      }
      List<UserDetails> users =
          properties.getUsers().stream()
              .map(
                  user ->
                      User.builder()
                          .username(user.getUsername())
                          .password(passwordEncoder.encode(user.getPassword()))
                          .authorities(user.getAuthorities())
                          .build())
              .toList();
      return new InMemoryUserDetailsManager(users);
    }
  }
}
