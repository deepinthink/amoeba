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
package org.deepinthink.amoeba.external.socket;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public interface SocketAcceptor {

  CompletionStage<Socket> accept(SetupPayload payload, Socket sending);

  static SocketAcceptor with(Socket socket) {
    return (setup, sending) -> CompletableFuture.completedStage(socket);
  }

  static SocketAcceptor forFireAndForget(Function<Payload, CompletionStage<Void>> handler) {
    return with(
        new Socket() {
          @Override
          public CompletionStage<Void> fireAndForget(Payload payload) {
            return handler.apply(payload);
          }
        });
  }

  static SocketAcceptor forRequestResponse(Function<Payload, CompletionStage<Payload>> handler) {
    return with(
        new Socket() {
          @Override
          public CompletionStage<Payload> requestResponse(Payload payload) {
            return handler.apply(payload);
          }
        });
  }
}
