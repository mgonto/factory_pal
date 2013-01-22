package ar.com.gonto.factorypal

import scala.language.experimental.macros
import scala.reflect.macros.Context

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
