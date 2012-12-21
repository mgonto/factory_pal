import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {


  lazy val root = Project(
    id = "factory_pal_sample",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "factory_pal_sample",
      organization := "ar.com.gonto",
      version := "0.1",
      scalaVersion := "2.10.0-RC3",
      scalacOptions += "",
      licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil,
      libraryDependencies ++= Seq(
       "org.scala-lang" % "scala-compiler" % "2.10.0-RC3",
       "ar.com.gonto" % "factory_pal_2.10" % "0.1.1-SNAPSHOT",
       "org.scalatest" % "scalatest_2.10.0-RC3" % "1.8-B1" % "test"
      ),
      resolvers ++= Seq(
         "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
         Resolver.url("Factory Pal Repository", 
          url("http://mgonto.github.com/snapshots/"))(Resolver.ivyStylePatterns)
      )

      // add other settings here
    )
  )
}
