/**
 * Licensed to Cloudera, Inc. under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Cloudera, Inc. licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cloudera.labs.envelope.validate;

import com.google.common.collect.Sets;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestAllowedValuesValidation {

  @Test
  public void testValid() {
    AllowedValuesValidation v = new AllowedValuesValidation("hello", 1, 2, 3);
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 2"));
    assertEquals(vr.getValidity(), Validity.VALID);
  }

  @Test
  public void testPathDoesntExist() {
    AllowedValuesValidation v = new AllowedValuesValidation("hello", 1, 2, 3);
    ValidationResult vr = v.validate(ConfigFactory.parseString("world = 2"));
    assertEquals(vr.getValidity(), Validity.VALID);
  }

  @Test
  public void testInvalid() {
    AllowedValuesValidation v = new AllowedValuesValidation("hello", 1, 2, 3);
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 4"));
    assertEquals(vr.getValidity(), Validity.INVALID);
  }

  @Test
  public void testKnownPaths() {
    AllowedValuesValidation v = new AllowedValuesValidation("hello", 1, 2, 3);
    assertEquals(Sets.newHashSet("hello"), v.getKnownPaths());
  }

}