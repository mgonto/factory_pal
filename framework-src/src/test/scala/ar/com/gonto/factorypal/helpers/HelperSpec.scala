package ar.com.gonto.factorypal
package helpers

import org.scalatest, scalatest.{FunSpec, matchers}, matchers.ShouldMatchers

case class Test(v: String)
case class Test2(v: String)

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

    it("should be possible to register multiple objects with unique symbols") {
      object Test2O extends PalObject[Test2] {
        def register() {
          FactoryPal.register[Test2]() { t =>
            t.v.mapsTo("stringValue")
          }
          FactoryPal.register[Test2](Some('byName)) { t =>
            t.v.mapsTo("nameValue")
          }
        }
      }

      Test2O.register()
      val noName = Test2O()
      val hasName = Test2O(Some('byName))
      noName should not be (null)
      noName.v should be ("stringValue")
      hasName should not be (null)
      hasName.v should be ("nameValue")
    }

  }

}
