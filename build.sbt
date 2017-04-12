// Name of the project
name := "Easy Stock"

// Project version
version := "0.0.0"

// Version of Scala used by the project
scalaVersion := "2.10.4"

// Add dependency on ScalaFX library
libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.0-R4"

// Fork a new JVM for 'run' and 'test:run', to avoid JavaFX double initialization problems
fork := true