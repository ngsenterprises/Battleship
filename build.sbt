name := "BattleShip"

version := "1.0.0"

scalaVersion := "2.12.4"

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % Test

logBuffered in Test := false

//libraryDependencies ++= Seq(
//  "org.scalatest" % "scalatest_2.12" % "3.0.1" % Test
//)

