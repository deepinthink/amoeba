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
package org.deepinthink.amoeba.spring.boot.vaadin;

import jakarta.annotation.Nonnull;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class VaadinScanPackagesRegistrar implements ImportBeanDefinitionRegistrar {
  public static final String VAADIN_SCAN_PACKAGES_CLASS =
      "com.vaadin.flow.spring.VaadinScanPackagesRegistrar$VaadinScanPackages";

  @Override
  public void registerBeanDefinitions(
      @Nonnull AnnotationMetadata annotationMetadata, @Nonnull BeanDefinitionRegistry registry) {
    String[] packages = getPackages(annotationMetadata, EnableVaadin.class, "value");
    if (packages.length > 0) {
      if (registry.containsBeanDefinition(VAADIN_SCAN_PACKAGES_CLASS)) {
        BeanDefinition beanDefinition = registry.getBeanDefinition(VAADIN_SCAN_PACKAGES_CLASS);
        ConstructorArgumentValues.ValueHolder valueHolder =
            beanDefinition.getConstructorArgumentValues().getIndexedArgumentValues().get(0);
        String[] existPackages = (String[]) valueHolder.getValue();
        List<String> packageList =
            existPackages == null
                ? new LinkedList<>()
                : new LinkedList<>(Arrays.asList(existPackages));
        packageList.addAll(Arrays.asList(packages));
        valueHolder.setValue(packageList.toArray(new String[0]));
      } else {
        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, packages);
        beanDefinition.setBeanClassName(VAADIN_SCAN_PACKAGES_CLASS);
        beanDefinition.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        registry.registerBeanDefinition(VAADIN_SCAN_PACKAGES_CLASS, beanDefinition);
      }
    }
  }

  private <T> T getPackages(
      Class<T> clazz,
      AnnotationMetadata annotationMetadata,
      Class<? extends Annotation> annotation,
      String getterName) {
    String annotationName = annotation.getName();
    if (annotationMetadata.hasAnnotation(annotationName)) {
      Map<String, Object> annotationAttributes =
          annotationMetadata.getAnnotationAttributes(annotationName);
      return annotationAttributes != null ? clazz.cast(annotationAttributes.get(getterName)) : null;
    }
    return null;
  }

  private String[] getPackages(
      AnnotationMetadata annotationMetadata,
      Class<? extends Annotation> annotation,
      String getterName) {
    String[] packages = getPackages(String[].class, annotationMetadata, annotation, getterName);
    return packages == null ? new String[0] : packages;
  }
}
