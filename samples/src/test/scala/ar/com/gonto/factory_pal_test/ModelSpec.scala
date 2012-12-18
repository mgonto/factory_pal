package ar.com.gonto.factory_pal_test

import org.scalatest.{BeforeAndAfter, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ar.com.gonto.factorypal.FactoryPal

/**
 * Test for model spec
 * @author mgonto
 * Created Date: 12/18/12
 */
class ModelSpec extends FunSpec with ShouldMatchers with BeforeAndAfter {

  before {
    FactoryPalRegisterer.register()
  }

  describe("Employee") {
    it("should return true for ceo name ok") {
      val employee = FactoryPal.create[Employee]
      employee.worksFor(employee.company.ceo.name) should be(true)
    }

    it("should return false for ceo name wrong") {
      val employee = FactoryPal.create[Employee]
      employee.worksFor("Mamamamamamamma") should be(false)
    }
  }

}
