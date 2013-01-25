/**
 * This sample uses the classes from the file Model.scala.
 * It illustrates the recommended use of a sealed trait and 
 * the implementation of the abstract class PalObject
 * in order to use the SpecHelper trait, which will make
 * working with multiple test objects much more concise.
 * Registering each individual test object is no longer
 * required inside the test specification. Instead,
 * all subclasses of the sealed trait will automatically
 * be registered with FactoryPal.
 *
 * @author Tim Steinbach
 * Created Date: Jan 25, 2013
 */

package ar.com.gonto.factory_pal_test

import org.scalatest.{BeforeAndAfterAll, FunSpec}
import org.scalatest.matchers.ShouldMatchers
import ar.com.gonto.factorypal.{FactoryPal, helpers}, helpers._

sealed trait TestObjects extends PalTrait

class EmployeeTest extends PalObject[Employee] with TestObjects {
  def register() = {
    FactoryPal.register[Employee]() { emp =>
      emp.name.mapsTo("employeeName") and
      emp.company.isAnotherFactoryModel and
      emp.age.mapsTo(225)
    }
  }
}

class CompanyTest extends PalObject[Company] with TestObjects {
  def register() = {
    FactoryPal.register[Company]() { company =>
      company.name.mapsTo("CompanyName") and
      company.ceo.isAnotherFactoryModel
    }
  }
}

class CeoTest extends PalObject[Ceo] with TestObjects {
  def register() = {
    FactoryPal.register[Ceo]() { ceo =>
      ceo.name.mapsTo("ceoName") and
      ceo.age.mapsTo(25)
    }
  }
}

class HelpersSpec extends FunSpec with ShouldMatchers with BeforeAndAfterAll with SpecHelper {

  override def beforeAll(configMap: Map[String, Any]) {
    register[TestObjects]()
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
