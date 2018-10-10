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
import com.typesafe.config.ConfigValueType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestExactlyOnePathExistsValidation {

  @Test
  public void testZeroExists() {
    Validation v = new ExactlyOnePathExistsValidation("hello", "world");
    ValidationResult vr = v.validate(ConfigFactory.parseString("test = 2"));
    assertEquals(vr.getValidity(), Validity.INVALID);
  }

  @Test
  public void testOneExists() {
    Validation v = new ExactlyOnePathExistsValidation("hello", "world");
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 2"));
    assertEquals(vr.getValidity(), Validity.VALID);
  }

  @Test
  public void testOneExistsWithType() {
    Validation v = new ExactlyOnePathExistsValidation(ConfigValueType.NUMBER, "hello", "world");
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 2"));
    assertEquals(vr.getValidity(), Validity.VALID);
  }

  @Test
  public void testMoreThanOneExists() {
    Validation v = new ExactlyOnePathExistsValidation("hello", "world");
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 2\nworld = 5"));
    assertEquals(vr.getValidity(), Validity.INVALID);
  }

  @Test
  public void testWrongType() {
    Validation v = new ExactlyOnePathExistsValidation(ConfigValueType.STRING, "hello", "world");
    ValidationResult vr = v.validate(ConfigFactory.parseString("hello = 2"));
    assertEquals(vr.getValidity(), Validity.INVALID);
  }

  @Test
  public void testKnownPaths() {
    Validation v = new ExactlyOnePathExistsValidation("hello", "world");
    assertEquals(Sets.newHashSet("hello", "world"), v.getKnownPaths());
  }

}
