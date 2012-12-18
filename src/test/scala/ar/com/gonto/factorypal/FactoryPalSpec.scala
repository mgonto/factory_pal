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

package ar.com.gonto.factorypal

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

/**
 * TODO: Add a comment
 * @author mgonto
 * Created Date: 12/18/12
 */
class FactoryPalSpec extends FunSpec with ShouldMatchers {

  describe("Factory pal") {
    it("should construct a person ok") {
      val name = "gonto"
      val age = 2
      FactoryPal.register[Person] { person =>
        person.name.mapsTo(name) and
        person.age.mapsTo(age)
      }

      val person = FactoryPal.create[Person]
      person.name should equal(name)
      person.age should equal(age)
    }
  }

}
