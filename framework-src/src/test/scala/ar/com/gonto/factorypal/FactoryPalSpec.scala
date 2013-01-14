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

import objects.ObjectBuilder
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
      val age = 2223
      FactoryPal.register[Person]() { person =>
        person.name.mapsTo(name) and
        person.age.mapsTo(age)
      }

      val person = FactoryPal.create[Person]
      person.name should equal(name)
      person.age should equal(age)
    }

    it("should construct an object with an Option value") {
      val value = Some("value")
      FactoryPal.register[Maybe]() { maybe =>
        maybe.value.mapsTo(value)
      }

      val maybe = FactoryPal.create[Maybe]
      maybe.value should equal(value)
    }

    it("should construct two models for person ok") {
      val name = "gonto"
      val age = 2223
      val johnName = "john"

      FactoryPal.register[Person]() { person =>
        person.name.mapsTo(name) and
          person.age.mapsTo(age)
      }

      FactoryPal.register[Person](Some('john)) { person =>
        person.name.mapsTo(johnName) and
          person.age.mapsTo(age)
      }

      val person = FactoryPal.create[Person]
      person.name should equal(name)
      person.age should equal(age)

      val johnPerson = FactoryPal.create[Person](Some('john))()
      johnPerson.name should equal(johnName)
    }

    it("should construct a person with overriders") {
      val name = "gonto"
      val age = 2223
      FactoryPal.register[Person]() { person =>
        person.name.mapsTo(name) and
          person.age.mapsTo(age)
      }

      val newName = "pepe"
      val person = FactoryPal.create[Person]() { person =>
        person.name.mapsTo(newName).alone
      }

      person.name should equal(newName)
      person.age should equal(age)
    }

    it("should construct an employee ok") {
      val companyName = "gonto"
      val employeeName = "gontoEmployee"
      FactoryPal.register[Company]() { company =>
        company.name.mapsTo(companyName).alone
      }

      FactoryPal.register[Employee]() { employee =>
        employee.name.mapsTo(employeeName) and
        employee.company.isAnotherFactoryModel
      }

      val employee = FactoryPal.create[Employee]

      employee.name should equal(employeeName)
      employee.company.name should equal(companyName)
    }
  }

}
