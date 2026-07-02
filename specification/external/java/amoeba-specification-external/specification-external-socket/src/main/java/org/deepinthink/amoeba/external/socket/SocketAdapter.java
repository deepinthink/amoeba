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

import static java.util.concurrent.CompletableFuture.failedFuture;

import java.util.concurrent.CompletionStage;

final class SocketAdapter {
  static final CompletionStage<Void> UNSUPPORTED_FIRE_AND_FORGET =
      failedFuture(new UnsupportedInteractionException("Fire-and-Forget"));
  static final CompletionStage<Payload> UNSUPPORTED_REQUEST_RESPONSE =
      failedFuture(new UnsupportedInteractionException("Request-Response"));
  static final CompletionStage<Void> UNSUPPORTED_METADATA_PUSH =
      failedFuture(new UnsupportedInteractionException("Metadata-Push"));

  static CompletionStage<Void> fireAndForget(Payload payload) {
    payload.release();
    return UNSUPPORTED_FIRE_AND_FORGET;
  }

  static CompletionStage<Payload> requestResponse(Payload payload) {
    payload.release();
    return UNSUPPORTED_REQUEST_RESPONSE;
  }

  static CompletionStage<Void> metadataPush(Payload payload) {
    payload.release();
    return UNSUPPORTED_METADATA_PUSH;
  }

  static class UnsupportedInteractionException extends RuntimeException {
    UnsupportedInteractionException(String interactionName) {
      super(interactionName + " not implemented.", null, false, false);
    }
  }
}
