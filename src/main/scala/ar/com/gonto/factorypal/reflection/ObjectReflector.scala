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
 * TODO: Add a comment
 * @author mgonto
 * Created Date: 12/10/12
 */
object ObjectReflector {

  def create[T, Any](fieldSetters : List[FieldSetter[T, Any]])(implicit man : Manifest[T]) = {
    fieldSetters.foreach(x => println(x.getValueClass))

    val constructorList = typeOf[T].declaration(nme.CONSTRUCTOR).asTerm.alternatives.collect {
        case m : MethodSymbol => m.paramss.map(_.map(x => x.asInstanceOf[TermSymbol]))
    }.flatten

    val minConstructor = constructorList.minBy(_.size)

    val namesToUse = minConstructor.map(x => (x.name.toString))

    val params = namesToUse.map(name =>
      fieldSetters.find(setter => setter.propName == name).getOrElse(
        throw new IllegalStateException(s"The constructor needs a param with name $name and there's no property with that value")
      ))

    val clazzToUse = clazz[T]

    val classesToUse = params.map(param => param.getValueClass)

    val reflectedConstructor = clazzToUse.getConstructor(classesToUse: _*)

    reflectedConstructor.newInstance(params.map(_.getValue.asInstanceOf[Object]) : _*).asInstanceOf[T]
  }

  private def clazz[T](implicit man : Manifest[T]) = man.runtimeClass


}
