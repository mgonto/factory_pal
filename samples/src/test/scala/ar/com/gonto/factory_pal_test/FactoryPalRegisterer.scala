package ar.com.gonto.factory_pal_test

import ar.com.gonto.factorypal.FactoryPal
import ar.com.gonto.factorypal.fields.FieldBuilder
/**
 * A registerer for FactoryPal
 * @author mgonto
 * Created Date: 12/18/12
 */
object FactoryPalRegisterer {

  def register() = {
    FactoryPal.register[Ceo] {ceo =>
      ceo.name.mapsTo("ceoName") and
      ceo.age.mapsTo(25)
    }

    FactoryPal.register[Company] {company =>
      company.name.mapsTo("CompanyName") and
      company.ceo.isAnotherFactoryModel
    }

    FactoryPal.register[Employee] {emp =>
        emp.name.mapsTo("employeeName") and
        emp.company.isAnotherFactoryModel and
        emp.age.mapsTo(225)
    }
  }

}
