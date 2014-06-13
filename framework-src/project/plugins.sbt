// Comment to get more information during initialization
logLevel := Level.Warn

// The Typesafe repository
resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/",
  "jgit-repo" at "http://download.eclipse.org/jgit/maven"
)

addSbtPlugin("com.typesafe.sbt" % "sbt-ghpages" % "0.5.2")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")
