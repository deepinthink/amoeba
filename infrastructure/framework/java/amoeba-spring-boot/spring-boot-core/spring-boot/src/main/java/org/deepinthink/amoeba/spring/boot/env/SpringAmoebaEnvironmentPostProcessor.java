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
package org.deepinthink.amoeba.spring.boot.env;

import java.util.Properties;
import org.deepinthink.amoeba.spring.boot.SpringAmoebaVersion;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.StringUtils;

public class SpringAmoebaEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

  public static final String SPRING_AMOEBA_DEFAULT_PROPERTY_SOURCE =
      "amoeba.spring-boot.default-properties";
  public static final String SPRING_AMOEBA_VERSION = "amoeba.spring-boot.version";
  public static final String SPRING_AMOEBA_FORMATTED_VERSION =
      "amoeba.spring-boot.formatted-version";
  public static final int ORDER = Ordered.LOWEST_PRECEDENCE - 100;

  @Override
  public void postProcessEnvironment(
      @NonNull ConfigurableEnvironment environment, @NonNull SpringApplication application) {
    addSpringAmoebaDefaultPropertySource(environment);
  }

  private void addSpringAmoebaDefaultPropertySource(ConfigurableEnvironment environment) {
    Properties defaultProperties = getSpringAmoebaVersionProperties();
    PropertiesPropertySource propertySource =
        new PropertiesPropertySource(SPRING_AMOEBA_DEFAULT_PROPERTY_SOURCE, defaultProperties);
    environment.getPropertySources().addLast(propertySource);
  }

  private Properties getSpringAmoebaVersionProperties() {
    Properties properties = new Properties();
    String version = getSpringAmoebaVersion();
    String formattedVersion = getFormattedSpringAmoebaVersion();
    properties.setProperty(SPRING_AMOEBA_VERSION, version);
    properties.setProperty(SPRING_AMOEBA_FORMATTED_VERSION, formattedVersion);
    return properties;
  }

  protected String getSpringAmoebaVersion() {
    return SpringAmoebaVersion.getVersion();
  }

  protected String getFormattedSpringAmoebaVersion() {
    String version = getSpringAmoebaVersion();
    return !StringUtils.hasText(version) ? "" : String.format(" (v%s)", version);
  }

  @Override
  public int getOrder() {
    return ORDER;
  }
}
