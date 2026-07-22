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
import org.deepinthink.amoeba.spring.boot.vaadin.EnableVaadin;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.views.VaadinAdminHeaderView;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.views.VaadinAdminMainLayout;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.views.VaadinAdminSideNavItemSupplier;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@AutoConfiguration
@EnableVaadin(VaadinAdminProperties.PREFIX_VIEWS)
@EnableConfigurationProperties(VaadinAdminProperties.class)
public class VaadinAdminAutoConfiguration {

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
}
