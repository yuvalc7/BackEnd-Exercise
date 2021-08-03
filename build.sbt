val sharedSettings = Seq(
  version := "0.0.1-SNAPSHOT",
  organization := "superstar candidate.sparkbeyond.exercise",
  scalaVersion := "2.13.6")

lazy val logic = (project in file("logic"))
  .settings(
    sharedSettings,
    name := "logic",
    libraryDependencies ++= Seq(
      "org.bouncycastle" % "bcpkix-jdk15on" % "1.69",
      "org.lz4"          % "lz4-java"       % "1.8.0"
    )
  )


lazy val server = (project in file("server"))
  .dependsOn(logic)
  .settings(
    sharedSettings,
    name := "server",
    libraryDependencies += "com.lihaoyi" %% "cask" % "0.7.11"
  )

lazy val root = (project in file("."))
  .aggregate(logic, server)
  .settings(
    name := "junior-backend-exercise"
  )
