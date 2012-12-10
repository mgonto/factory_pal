package ar.com.gonto.factorypal.fields

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
 * TODO: Write me
 *
 * @author mgonto
 *         Created Date: 12/10/12
 */
class FieldBuilder[O, F](val propName: String) {

  def mapsTo(value : F) : FieldSetter[O, F] =
    new SpecifiedFieldSetter[O, F](propName, value)

}

object FieldBuilder {

  def apply[O, F](propName: String) =
    new FieldBuilder[O, F](propName)

}
