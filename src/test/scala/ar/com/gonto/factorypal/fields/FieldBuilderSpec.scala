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

package ar.com.gonto.factorypal.fields

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ar.com.gonto.factorypal.Person

/**
 * TODO: Add a comment
 * @author mgonto
 * Created Date: 12/10/12
 */
class FieldBuilderSpec extends FunSpec with ShouldMatchers {

  describe ("A field builder") {
    it ("should be able to map a property to a value") {
      val propName = "age"
      val fieldBuilder = new FieldBuilder(propName) {
        type objectType = Person
        type fieldType = Int
      }
      val age = 55
      val setter = fieldBuilder mapsTo(age)
      setter should not equal(null)
      setter.propName should equal(propName)
    }

    it ("should be able to map to a random value") {
      val propName = "age"
      val fieldBuilder = new FieldBuilder(propName) {
        type objectType = Person
        type fieldType = Int
      }
      val setter = fieldBuilder.isRandom
      setter should not equal(null)
      setter.getValue should not equal(null)
    }
  }

}
