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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = VaadinAdminProperties.PREFIX)
public class VaadinAdminProperties {
  public static final String PREFIX = "amoeba.vaadin.admin";

  public static final String PREFIX_VIEWS = "org.deepinthink.amoeba.spring.boot.vaadin.admin.views";

  @NestedConfigurationProperty
  private final VaadinAdminLoginProperties login = new VaadinAdminLoginProperties();

  @NestedConfigurationProperty
  private final VaadinAdminHeaderProperties header = new VaadinAdminHeaderProperties();

  public VaadinAdminLoginProperties getLogin() {
    return login;
  }

  public VaadinAdminHeaderProperties getHeader() {
    return header;
  }
}
