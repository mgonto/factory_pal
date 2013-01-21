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

import objects.ObjectSetter
import util.Random
import scala.language.implicitConversions

/**
 * This package object will have the implicit conversion to turn a FieldSetter to an
 * ObjectSetter with only one setter
 * @author mgonto
 */
package object fields {

  implicit def fieldSetterToObjectSetter[O](setter : FieldSetter[O, Any]) =
    new ObjectSetter[O](List(setter))

  /**
   * This are classes that can create random values
   * @author mgonto
   * Created Date: 12/18/12
   */
  trait Randomizer[T] extends (() => T){

    val random = new Random()

    def apply() : T
  }

  implicit object IntRandomizer extends Randomizer[Int] {

    def apply() : Int = random.nextInt()
  }

  implicit object DoubleRandomizer extends Randomizer[Double] {
    def apply() : Double = random.nextDouble()
  }

  implicit object LongRandomizer extends Randomizer[Long] {
    def apply() : Long = random.nextLong()
  }

  implicit object StringRandomizer extends Randomizer[String] {
    def apply() : String = random.nextString(10)
  }

}
