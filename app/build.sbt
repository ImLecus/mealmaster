ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.3"

lazy val root = (project in file("."))
  .settings(
    name := "app"
  )
libraryDependencies += "org.scalafx" %% "scalafx" % "22.0.0-R33"