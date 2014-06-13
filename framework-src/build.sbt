name := "factory_pal"

organization := "ar.com.gonto"

version := "0.2.2"

scalaVersion := "2.11.1"

scalacOptions := Seq("-deprecation", "-feature")

licenses      := ("Apache2", new java.net.URL("http://www.apache.org/licenses/LICENSE-2.0.txt")) :: Nil

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.0" % "test",
  "org.scala-lang" % "scala-compiler" % "2.11.1"
)

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "OSS SonarType" at "https://oss.sonatype.org/content/groups/public/"
)

site.settings

ghpages.settings

git.remoteRepo := "git@github.com:salsify/salsify.github.io.git"

git.gitCurrentBranch := "master"

git.branch := Some("master")
