import Dependencies._

name := "ScalaAvroKafka"

organization := "mveeprojects"

version := "0.1"

scalaVersion := "2.13.4"

libraryDependencies ++= (kafkaDependencies ++ configDependencies ++ loggingDependencies)

resolvers in ThisBuild ++= Seq("confluent-release" at "https://packages.confluent.io/maven/")
