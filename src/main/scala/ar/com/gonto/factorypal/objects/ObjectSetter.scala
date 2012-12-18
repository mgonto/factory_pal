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

package ar.com.gonto.factorypal.objects

import ar.com.gonto.factorypal.fields.FieldSetter

/**
 * This class holds a List of FieldSetters.
 *
 * This class was mainly created for syntactic sugar for the user
 * @author mgonto
 * Created Date: 12/18/12
 */
class ObjectSetter[+O](val fieldSetters : List[FieldSetter[O, Any]]) {

def and[B >: O](setter : FieldSetter[B, Any]) = new ObjectSetter[B](setter :: fieldSetters)

def overrideFields[B >: O](objectSetter : ObjectSetter[B]) = {
  new ObjectSetter[B](fieldSetters.filter(setter =>
    !objectSetter.fieldSetters.contains(setter)) ++ objectSetter.fieldSetters)
}
}
