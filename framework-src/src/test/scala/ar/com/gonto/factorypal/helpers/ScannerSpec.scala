package ar.com.gonto.factorypal
package helpers

import scala.util.Success
import org.scalatest, scalatest.{FunSpec, matchers}, matchers.ShouldMatchers

sealed trait Tests extends PalTrait

case class RealObject1(v: String)
case class RealObject2(v: String, i: Int)

class PalObject1 extends PalObject[RealObject1] with Tests {
  def register() = {
    FactoryPal.register[RealObject1]() { r =>
      r.v.mapsTo("value1")
    }
  }
}

class PalObject2 extends PalObject[RealObject2] with Tests {
  def register() = {
    FactoryPal.register[RealObject2]() { r =>
      r.v.mapsTo("value2") and
      r.i.mapsTo(2)
    }
  }
}

class ScannerSpec extends FunSpec with ShouldMatchers with SpecHelper[Tests] {

  describe("Scanner") {

    it("should find all subclasses of the sealed trait Tests") {
      val subs = Scanner.subclasses[Tests]
      subs.size should be(2)
    }

  }

  describe("PalTrait") {

    it("should register all subclasses of trait Tests, so that they can be retrieved") {
      val ro1t = new PalObject1().create()
      val ro2t = new PalObject2().create()

      ro1t.isInstanceOf[Success[_]] should be(true)
      ro2t.isInstanceOf[Success[_]] should be(true)

      val ro1 = ro1t.get
      val ro2 = ro2t.get
      ro1.v should be("value1")
      ro2.v should be("value2")
      ro2.i should be(2)
    }

  }

}
