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

package ar.com.gonto.factorypal.reflection

import ar.com.gonto.factorypal.objects.ObjectBuilder
import scala.reflect.runtime.universe._
import ar.com.gonto.factorypal.fields.FieldSetter

/**
 * This is the object that does the magic.
 *
 * Given a list of FieldSetters and the Type of the object to create, this object first finds
 * the minimum constructor to which we have all of the proeprties needed in our FieldSetters.
 * After this, using this constructor, it instantiates the object. Then, using the remaining
 * FieldSetters, this sets all of the fields value the user has asked.
 * @author mgonto
 */
object ObjectReflector {

  def create[T, Any](fieldSetters : List[FieldSetter[T, Any]])(implicit man : Manifest[T]) : T = {

    val constructorList = typeOf[T].declaration(nme.CONSTRUCTOR).asTerm.alternatives.collect {
        case m : MethodSymbol => m.paramss.map(_.map(x => x.asInstanceOf[TermSymbol]))
    }.flatten

    val minConstructor = constructorList.minBy(_.size)

    val namesToUse = minConstructor.map(x => (x.name.toString))

    val clazzToUse = clazz[T]
    val clazzName = clazzToUse.getSimpleName

    val params = namesToUse.map(name =>
      fieldSetters.find(setter => setter.propName == name).getOrElse(
        throw new IllegalStateException(s"The constructor for $clazzName needs a param with name $name and there's no property with that value")
      ))

    val classesToUse = params.map(param => param.getValueClass)

    val reflectedConstructor = clazzToUse.getConstructor(classesToUse: _*)

    val instance = reflectedConstructor.newInstance(params.map(_.getValue.asInstanceOf[Object]) : _*).asInstanceOf[T]

    val fieldsRemaining = fieldSetters.dropWhile(elem => params.contains(elem))

    fieldsRemaining.foreach(_.setValue(instance))

    instance
  }

  def clazz[T](implicit man : Manifest[T]) = man.runtimeClass

  def classSymbol[T](implicit man : Manifest[T]) = Symbol(clazz[T].getName)


}
