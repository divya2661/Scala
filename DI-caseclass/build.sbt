name := "galileo Sessionizer"

version := "0.0.1"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.8.3" % "test"
)

// Read here for optional dependencies:
// https://etorreborre.github.io/specs2/guide/SPECS2-3.8.3/org.specs2.guide.Installation.html#other-dependencies
//val paradiseVersion = "2.1.0-M5"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.1.0" % "compile"

libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.1.0" % "compile"

libraryDependencies += "org.scalaz" %% "scalaz-typelevel" % "7.1.0"  % "compile"


