name := "IncWH"

version := "1.0"

lazy val `incwh` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc , cache , ws   , specs2 % Test,
  "com.typesafe.play" % "play-json_2.11" % "2.4.2",
   "org.scalaj" % "scalaj-http_2.11" % "2.3.0",
  "net.liftweb" %% "lift-json" % "2.6",
  "com.github.gilbertw1" %% "slack-scala-client" % "0.1.7"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  