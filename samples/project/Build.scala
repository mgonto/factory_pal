import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {

  lazy val root = Project(
    id = "factory_pal_sample",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "factory_pal_sample",
      organization := "ar.com.gonto",
      version := "0.2",
      scalaVersion := "2.10.0",
      scalacOptions += "",
      licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
      libraryDependencies ++= Seq(
       "org.scala-lang" % "scala-compiler" % "2.10.0",
       "ar.com.gonto" % "factory_pal_2.10" % "0.2",
       "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test"
      ),
      resolvers ++= Seq(
         "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
         Resolver.url("Factory Pal Repository", 
          url("http://mgonto.github.com/releases/"))(Resolver.ivyStylePatterns)
      )
    )
  )
}
