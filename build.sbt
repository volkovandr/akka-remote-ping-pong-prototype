name := "akka-multinode-ping-pong"

lazy val commonSettings = Seq(
  version := "0.1",
  scalaVersion := "2.12.4",
  dockerBaseImage := "artifactory.tech-on-air.com/bcs-java:latest",
  version in Docker := version.value
)

lazy val commonLibs = Seq(
  "com.typesafe.scala-logging" %% "scala-logging"   % "3.7.2",
  "ch.qos.logback"              % "logback-classic" % "1.2.3",
  "com.typesafe"                % "config"          % "1.3.1",
  "com.typesafe.akka"          %% "akka-remote"     % "2.5.8",
  "com.typesafe.akka"          %% "akka-actor"      % "2.5.8"
)

lazy val pinger = (project in file("pinger"))
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .settings(
    inThisBuild(commonSettings),
    name := "pinger",
    libraryDependencies ++= commonLibs,
    packageName in Docker := "pinger",
    dockerExposedPorts ++= Seq(8000)
  )

lazy val ponger = (project in file("ponger"))
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .settings(
    inThisBuild(commonSettings),
    name := "ponger",
    libraryDependencies ++= commonLibs,
    packageName in Docker := "ponger",
    dockerExposedPorts ++= Seq(8000)
  )

lazy val main = (project in file("main"))
  .enablePlugins(DockerPlugin)
  .enablePlugins(JavaAppPackaging)
  .settings(
    inThisBuild(commonSettings),
    name := "main",
    libraryDependencies ++= commonLibs,
    packageName in Docker := "main",
    dockerExposedPorts ++= Seq(8000)
  )
  .dependsOn(pinger)
  .dependsOn(ponger)

