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
package org.deepinthink.amoeba.spring.boot.admin.admin.vaadin.autoconfigure;

import com.vaadin.flow.component.sidenav.SideNavItem;
import org.deepinthink.amoeba.spring.boot.vaadin.EnableVaadin;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.views.VaadinAdminSideNavItemSupplier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableVaadin(AdminAdminVaadinProperties.PREFIX_VIEWS)
@EnableConfigurationProperties(AdminAdminVaadinProperties.class)
public class AdminAdminVaadinAutoConfiguration {

  @Bean
  public VaadinAdminSideNavItemSupplier adminAdminVaadinSideNavView(
      AdminAdminVaadinProperties properties) {
    return (context) -> new SideNavItem(properties.getLabel());
  }
}
