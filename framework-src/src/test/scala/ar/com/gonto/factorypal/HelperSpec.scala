package ar.com.gonto.factorypal

import org.scalatest, scalatest.{FunSpec, matchers}, matchers.ShouldMatchers

case class Milk(fatPerc: Int)

object MilkO extends PalObject[Milk] {
  def register() = {
    FactoryPal.register[Milk]() { m =>
      m.fatPerc.mapsTo(2)
    }
  }
}

class HelperSpec extends FunSpec with ShouldMatchers {

  describe("Helper") {
    it("should be possible to define new PalObjects") {
      case class Test(v: String)
      object TestO extends PalObject[Test] {
        def register() {
          FactoryPal.register[Test]() { t =>
            t.v.mapsTo("stringValue")
          }
        }
      }
    }

    it("should be possible to register and retrieve PalObjects") {
      MilkO.register()
      val obj = MilkO()
      obj should not be (null)
      obj.fatPerc should be (2)
    }
  }

}
