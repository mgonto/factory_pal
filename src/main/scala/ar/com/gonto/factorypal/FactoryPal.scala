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

import objects.{ObjectSetter, ObjectBuilder}
import reflection.ObjectReflector


/**
 * This is the main interface for the user with the Framework
 * @author mgonto
 */
object FactoryPal {

  private var models : Map[Symbol, ObjectSetter[Any]] = Map()

  def register[O](model : ObjectBuilder[O] => ObjectSetter[O])(implicit man : Manifest[O]) {
    val symbol = ObjectReflector.classSymbol[O]
    models = models updated(symbol, model(ObjectBuilder[O]()))
  }

  def create[O](implicit man : Manifest[O]) : O = {
    val symbol = ObjectReflector.classSymbol[O]
    val creator = models.get(symbol).getOrElse(
      throw new IllegalStateException(s"No builder register for $symbol")
    ).asInstanceOf[ObjectSetter[O]]
    ObjectReflector.create(creator.fieldSetters)
  }


}
