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

import ar.com.gonto.factorypal.FactoryPal

/**
 * This is a FieldSetter that will be mapped to another model registered in FactoryPal
 * This will be used for nested creation
 * @author mgonto
 * Created Date: 12/18/12
 */
class OtherModelFieldSetter[O, F](propName: String, clazz: Class[_])
  extends FieldSetter[O, F](propName,clazz) {

  def getValue: F = FactoryPal.create[F]()()(Manifest.classType[F](clazz))
}
