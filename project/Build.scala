import sbt._
import sbt.Keys._

object ApplicationBuild extends Build {


  lazy val root = Project(
    id = "factory_pal",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "factory_pal",
      organization := "ar.com.gonto",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.0-RC3",
      libraryDependencies ++= Seq(
       "org.scalatest" % "scalatest_2.10.0-RC3" % "1.8-B1"
      ),
      resolvers ++= Seq(
         "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
         "OSS SonarType" at "https://oss.sonatype.org/content/groups/public/"
      )

      // add other settings here
    )
  )
}
