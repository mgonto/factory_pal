package ar.com.gonto.factorypal
package helpers

import scala.util.Try
import scala.reflect.runtime.universe.TypeTag

/**
* By defining an object as a subtype of this abstract class, using FactoryPal
* becomes much easier. This class provides convenient access to the register method
* defined on the FactoryPal object and requires a register method to be implemented.

* Example:
*  case class Milk(fatPerc: Int)
*
*  object MilkO extends PalObject[Milk] {
*   def register() = {
*     FactoryPal.register[Milk]() { m =>
*       m.fatPerc.mapsTo(2)
*     }
*   }
* }
*
* An object for testing can now be created simply be calling MilkO().
*
*/
abstract class PalObject[T](implicit m: Manifest[T]) {

  def create(symbol: Option[Symbol] = None) = Try(FactoryPal.create[T](symbol)())

  def apply(symbol: Option[Symbol] = None) = FactoryPal.create[T](symbol)()

}

/**
* @see SpecHelper
*/
trait PalTrait {
  def register(): Unit
}

/**
  Automatically register all subclasses of a sealed trait.
  This greatly simplifies using FactoryPal with multiple objects.

  Simply create a **sealed trait** that extends PalTrait and have all your
  test objects inherit from PalObject as well as this new trait.
  Then include this trait in your test specification and call register()
  and everything else will be taken care of. No need to register each individual
  test object.

  Example:
    sealed trait MyTestObjects extends PalTrait
    class MyTestObject1 extends PalObject[MyObject1] with MyTestObjects {
      def register() = {
        FactoryPal.register[MyObject1]() { o =>
          o.id.mapsTo("ID1") and
          o.name.mapsTo("Name1")
        }
      }
    }
    class MyTestObject2 extends PalObject[MyObject2] with MyTestObjects {
      def register() = {
        FactoryPal.register[MyObject2]() { o =>
          o.id.mapsTo("ID2") and
          o.value.mapsTo("Value2")
        }
      }
    }
    class ScannerSpec extends FunSpec with ShouldMatchers with SpecHelper {
      register[MyTestObjects]()
      // Your tests here
    }
*/
trait SpecHelper {
  def register[T: TypeTag]() {
    def sym2pal = (x: reflect.runtime.universe.Symbol) => Class.forName(x.fullName).newInstance.asInstanceOf[PalTrait]
    Scanner.sealedDescendants[T] map sym2pal foreach {_.register()}
  }
}
