import java.io.File

name := "Easy Stock"

version := "0.0.1"

organization := "luis.arce22@gmail.com"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.scalafx" %% "scalafx" % "8.0.92-R10",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "org.scala-lang" % "scala-library" % "2.11.2",
  "edu.uci.ics" % "crawler4j" % "3.4",
  "mysql" % "mysql-connector-java" % "5.1.9",
  "org.hibernate" % "hibernate-entitymanager" % "3.3.2.GA",
  "dom4j" % "dom4j" % "1.6.1",
  "commons-logging" % "commons-logging" % "1.1.1",
  "commons-collections" % "commons-collections" % "3.2.1",
  "cglib" % "cglib" % "2.2",
  "javax.transaction" % "jta" % "1.1")

resolvers += Opts.resolver.sonatypeSnapshots

scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xlint")

// Sources should also be copied to output, so the sample code, for the viewer,
// can be loaded from the same file that is used to execute the example
unmanagedResourceDirectories in Compile <+= baseDirectory {_ / "src/main/scala"}

// Set the prompt (for this build) to include the project id.
shellPrompt := { state => System.getProperty("user.name") + ":" + Project.extract(state).currentRef.project + "> " }

// Run in separate VM, so there are no issues with double initialization of JavaFX
fork := true

fork in Test := true

// Create file used to determine available examples at runtime.
resourceGenerators in Compile <+= Def.task {
  /** Scan source directory for available examples
    * Return pairs 'directory' -> 'collection of examples in that directory'.
    */
  def loadExampleNames(inSourceDir: File): Array[(String, Array[String])] = {
    val examplesDir = "/scalafx/ecstock/ui/"
    val examplePath = new File(inSourceDir, examplesDir)
    val exampleRootFiles = examplePath.listFiles()
    for (dir <- exampleRootFiles if dir.isDirectory) yield {
      val leaves = for (f <- dir.listFiles() if f.getName.contains(".scala")) yield {
        f.getName.stripSuffix(".scala").stripPrefix("EcStock")
      }
      dir.getName.capitalize -> leaves.sorted
    }
  }

  /** Create file representing names and directories for all availabe examples.
    * It will be loaded by the application at runtime and used to popolate example tree.
    */
  def generateExampleTreeFile(inSourceDir: File,
                              outSourceDir: File,
                              templatePath: String): Seq[File] = {
    val exampleDirs = loadExampleNames(inSourceDir)
    val contents = exampleDirs.map { case (dir, leaves) => dir + " -> " + leaves.mkString(", ") }.mkString("\n")
    val outFile = new File(outSourceDir, templatePath)
    IO.write(outFile, contents)
    Seq(outFile)
  }

  generateExampleTreeFile(
    (scalaSource in Compile).value,
    (resourceManaged in Compile).value,
    "/scalafx/ecstock/ui/ui.tree"
  )
}

mainClass in Compile := Some("scalafx.ecstock.EcStock")
mainClass in assembly := Some("scalafx.ecstock.EcStock")

//
// Configuration for sbt-native-packager / JDKPackagerPlugin
//

enablePlugins(JDKPackagerPlugin)

maintainer := "Luis Arce"
packageSummary := "Inventory System"
packageDescription := "An application to manage inventory."

lazy val iconGlob = sys.props("os.name").toLowerCase match {
  case os if os.contains("mac") => "*.icns"
  case os if os.contains("win") => "*.ico"
  case _ => "*.png"
}

jdkAppIcon := (sourceDirectory.value ** iconGlob).getPaths.headOption.map(file)
jdkPackagerType := "installer"

// this is to help ubuntu 15.10
antPackagerTasks in JDKPackager := (antPackagerTasks in JDKPackager).value orElse {
  for {
    f <- Some(file("/usr/lib/jvm/java-8-oracle/lib/ant-javafx.jar")) if f.exists()
  } yield f
}
