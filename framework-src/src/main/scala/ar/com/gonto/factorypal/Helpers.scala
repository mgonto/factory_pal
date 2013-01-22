package ar.com.gonto.factorypal

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

  def apply(symbol: Option[Symbol] = None) = FactoryPal.create[T](symbol)()

  def register(): Unit

}

trait PalTrait {
  def register(): Unit
}

trait SpecHelper[T <: PalTrait] {
  Scanner.subclasses[T] foreach {_.register()}
}



