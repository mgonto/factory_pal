/*
 * *
 *  * Copyright 2012 Martin Gontovnikas (martin at gonto dot com dot ar) - twitter: @mgonto
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 *
 */

package ar.com.gonto.factorypal.configuration

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * Tests that all of the configuration is finished
 * @author mgonto
 *         Created Date: 12/9/12
 */
class ConfigurationSpec extends FunSpec with ShouldMatchers {

  describe("Configuration is OK if test runs ok") {

    it("should be 2 the sum of 1 + 1") {
      (1 + 1) should equal(2)
    }

    it("should have experimental active") {
      DynamicTestObject.callDynamicMethod() should
        equal("The method callDynamicMethod was called")
    }
  }


}
