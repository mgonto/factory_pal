package ar.com.gonto.factory_pal_test

/**
 * The model for my application
 * @author mgonto
 */
case class Employee(company : Company, name : String, age : Int) {
  def worksFor(ceo : Ceo) = company.ceo == ceo
  def worksFor(bossName: String) = company.ceo.name == bossName
}

case class Company(name : String, ceo : Ceo)

case class Ceo(name : String, age : Int)
