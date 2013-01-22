package ar.com.gonto.factorypal

import org.scalatest, scalatest.{FunSpec, matchers}, matchers.ShouldMatchers

sealed trait Tests extends PalTrait {
  def ping = false
}

case class RealObject1()
case class RealObject2()

class PalObject1 extends PalObject[RealObject1] with Tests {
  def register() = {}
}
class PalObject2 extends PalObject[RealObject2] with Tests {
  def register() = {}
}

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

  describe("Scanner") {

    it("should find all subclasses of the sealed trait Tests") {
      val subs = Scanner.subclasses[Tests]
      subs.size should be(2)
      subs.map{_.ping}.foldLeft(true)(_ && _) should be(false)
    }

  }

}
