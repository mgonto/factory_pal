package ar.com.gonto.factorypal.helpers

import scala.reflect.runtime.universe._

object Scanner {

  def sealedDescendants[Root: TypeTag]: Set[Symbol] = {
    val symbol = typeOf[Root].typeSymbol
    val internal = symbol.asInstanceOf[scala.reflect.internal.Symbols#Symbol]
    if (internal.isSealed) {
      (internal.sealedDescendants.map(_.asInstanceOf[Symbol]) - symbol)
    } else {
      Set.empty
    }
  }

}
