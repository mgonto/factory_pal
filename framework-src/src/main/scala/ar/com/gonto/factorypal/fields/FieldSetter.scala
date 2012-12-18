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

import ar.com.gonto.factorypal.reflection.FieldReflector
import ar.com.gonto.factorypal.objects.ObjectSetter

/**
 * A FieldSetter is used to set a certain field to an instance of an object.
 *
 * The result of configuring ObjectBuilders with FieldBuilders is a list of FieldSetters
 * that can create the object given what the user stated in the model.
 * @author mgonto
 */
abstract class FieldSetter[+O, +F](val propName: String, clazz: Class[_]) {

  def setValue[B >: O](obj : B) {
    new FieldReflector(obj).setV(propName, getValue)
  }

  def getValue : F

  def alone = new ObjectSetter[O](List(this))

  def getValueClass : Class[_] = clazz
}
