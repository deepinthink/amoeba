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
package org.deepinthink.amoeba.external.socket.metadata;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum WellKnownMimeType {
  UNPARSEABLE_MIME_TYPE("UNPARSEABLE_MIME_TYPE_DO_NOT_USE", (byte) -2),
  UNKNOWN_RESERVED_MIME_TYPE("UNKNOWN_YET_RESERVED_DO_NOT_USE", (byte) -1);

  static final WellKnownMimeType[] TYPES_BY_MIME_ID;
  static final Map<String, WellKnownMimeType> TYPES_BY_MIME_STRING;

  static {
    TYPES_BY_MIME_ID = new WellKnownMimeType[128];
    Arrays.fill(TYPES_BY_MIME_ID, UNKNOWN_RESERVED_MIME_TYPE);
    TYPES_BY_MIME_STRING = new HashMap<>(128);

    for (WellKnownMimeType value : values()) {
      if (value.getIdentifier() >= 0) {
        TYPES_BY_MIME_ID[value.getIdentifier()] = value;
        TYPES_BY_MIME_STRING.put(value.getString(), value);
      }
    }
  }

  private final byte identifier;
  private final String str;

  WellKnownMimeType(String str, byte identifier) {
    this.str = str;
    this.identifier = identifier;
  }

  public static WellKnownMimeType fromIdentifier(int id) {
    if (id < 0x00 || id > 0x7F) {
      return UNPARSEABLE_MIME_TYPE;
    }
    return TYPES_BY_MIME_ID[id];
  }

  public byte getIdentifier() {
    return identifier;
  }

  public String getString() {
    return str;
  }

  @Override
  public String toString() {
    return str;
  }
}
