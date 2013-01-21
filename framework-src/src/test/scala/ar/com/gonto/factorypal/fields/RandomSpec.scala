package ar.com.gonto.factorypal.fields

import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import ar.com.gonto.factorypal.{Age, Person, FactoryPal}

/**
 * Test for Random fields
 * @author mgonto
 * Created Date: 1/21/13
 */
class RandomSpec extends FunSpec with ShouldMatchers {

describe("Random fields") {
  it("should be created implicitly for some types") {
    FactoryPal.register[Age]() { person =>
      person.age.isRandom.alone
    }

    val person = FactoryPal.create[Age]


  }

  it("should be created manually") {
    FactoryPal.register[Age]() { person =>
        person.age.isRandom(() => 4).alone
    }

    val person = FactoryPal.create[Age]
    person.age should be(4)

  }
}

}
