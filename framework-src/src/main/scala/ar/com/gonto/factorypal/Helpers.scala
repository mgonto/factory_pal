package ar.com.gonto.factorypal

import scala.language.experimental.macros
import scala.reflect.macros.Context

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

// trait SpecHelper[T <: PalTrait] {
//   Scanner.subclasses[T] foreach {_.register()}
// }


object Scanner {
  def subclasses[A]: Set[A] = macro subclasses_impl[A]

  def subclasses_impl[A: c.WeakTypeTag](c: Context) = {
    import c.universe._

    val symbol = weakTypeOf[A].typeSymbol

    val children = if ((symbol.isClass) && (symbol.asClass.isSealed)) {
      symbol.asClass.knownDirectSubclasses.toList //.filter{_.isModuleClass}
      } else {
        List()
      }

      c.Expr[Set[A]](Apply(Select(reify(Set).tree, "apply"), children.map(New(_))))
    }
  }
