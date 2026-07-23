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
package org.deepinthink.amoeba.spring.boot.vaadin.admin.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.deepinthink.amoeba.spring.boot.vaadin.admin.autoconfigure.VaadinAdminLoginProperties;

@Route("login")
@AnonymousAllowed
public class VaadinAdminLoginView extends VerticalLayout implements BeforeEnterObserver {
  private final LoginForm login = new LoginForm();

  public VaadinAdminLoginView(VaadinAdminLoginProperties properties) {
    addClassName("login-view");
    setSizeFull();
    setAlignItems(Alignment.CENTER);
    setJustifyContentMode(JustifyContentMode.CENTER);
    LoginOverlay login = new LoginOverlay();
    login.setForgotPasswordButtonVisible(false);
    login.setOpened(true);
    login.setTitle(properties.getTitle());
    login.setDescription(properties.getDescription());
    login.setAction("login");
    add(login);
  }

  @Override
  public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
    if (beforeEnterEvent.getLocation().getQueryParameters().getParameters().containsKey("error")) {
      login.setEnabled(true);
    }
  }
}
