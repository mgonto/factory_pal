package ar.com.gonto.factorypal.fields

import ar.com.gonto.factorypal.reflection.ObjectReflector

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

/**
 * This class works together with the ObjectBuilder to let users create some
 * FieldSetter for a certain field in a certain object type.
 *
 * It has 2 abstract types:
 * ObjectType is the type of the object to build
 * FieldType is the type of the field to set
 *
 * This will ensure that everything is type safe
 *
 * @author mgonto
 */
class FieldBuilder(val propName: String) {

  type objectType
  type fieldType

  def mapsTo(value : fieldType)(implicit man : Manifest[fieldType]) : FieldSetter[objectType, fieldType] = {
    new SpecifiedFieldSetter[objectType, fieldType](propName, value, fieldClass)
  }

  def isRandom(implicit random : Randomizer[fieldType], man : Manifest[fieldType]) : FieldSetter[objectType, fieldType] =
    mapsTo(random.randomValue)

  def isAnotherFactoryModel(implicit man : Manifest[fieldType]) =
    new OtherModelFieldSetter[objectType, fieldType](propName, fieldClass)

  private def fieldClass(implicit man : Manifest[fieldType]) = ObjectReflector.clazz[fieldType]

}

