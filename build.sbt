val scala3Version = "3.2.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "persimmon_backend",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-api" % "1.7.36",
        "org.slf4j" % "slf4j-simple" % "1.7.36",
        "com.slack.api" % "bolt" % "1.18.0",
        "com.slack.api" % "bolt-socket-mode" % "1.18.0",
        "javax.websocket" % "javax.websocket-api" % "1.1",
        "org.glassfish.tyrus.bundles" % "tyrus-standalone-client" % "1.17",
        "org.scalameta" %% "munit" % "0.7.29" % Test
    )
  )
