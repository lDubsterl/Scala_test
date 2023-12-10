version := "0.1.0-SNAPSHOT"

scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "Test_tasks"
  )

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "2.3.0"
libraryDependencies += "org.apache.opennlp" % "opennlp-tools" % "2.3.1"
libraryDependencies += "org.apache.commons" % "commons-math3" % "3.6.1"
