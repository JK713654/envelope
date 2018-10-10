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
package com.cloudera.labs.envelope.hbase;

import com.cloudera.labs.envelope.validate.Validation;
import com.cloudera.labs.envelope.validate.ValidationResult;
import com.cloudera.labs.envelope.validate.Validity;
import com.google.common.collect.Sets;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigObject;

import java.util.Set;

public class HBaseColumnsValidation implements Validation {
  
  @Override
  public ValidationResult validate(Config config) {
    if (config.hasPath(HBaseUtils.COLUMNS_PROPERTY)) {
      ConfigObject columnConfig = config.getConfig(HBaseUtils.COLUMNS_PROPERTY).root();
      Set<String> columns = columnConfig.keySet();
      for (String column : columns) {
        if (!config.hasPath(HBaseUtils.COLUMNS_PROPERTY + "." + column + ".col")) {
          return new ValidationResult(Validity.INVALID, "'col' not specified in column " + column);
        }
        if (!config.hasPath(HBaseUtils.COLUMNS_PROPERTY + "." + column + ".type")) {
          return new ValidationResult(Validity.INVALID, "'type' not specified in column " + column);
        }
        if (!config.hasPath(HBaseUtils.COLUMNS_PROPERTY + "." + column + ".cf")) {
          return new ValidationResult(Validity.INVALID, "'cf' not specified in column " + column);
        }
      }
    }
    
    return new ValidationResult(Validity.VALID, "HBase column entries are all valid");
  }

  @Override
  public Set<String> getKnownPaths() {
    return Sets.newHashSet(HBaseUtils.TABLE_NAME_PROPERTY, HBaseUtils.ROWKEY_PROPERTY, HBaseUtils.COLUMNS_PROPERTY);
  }
  
}