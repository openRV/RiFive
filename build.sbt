ThisBuild / version := "1.0"
ThisBuild / scalaVersion := "2.11.12"
ThisBuild / organization := "cn.edu.whu.wuhanuniversity"

lazy val riscv = (project in file("."))
  .settings(
    name := "RiFive",
    libraryDependencies ++= Seq(spinalCore, spinalLib, spinalIdslPlugin)
  )
val spinalVersion = "1.6.0"
val spinalCore = "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion
val spinalLib = "com.github.spinalhdl" %% "spinalhdl-lib" % spinalVersion
val spinalIdslPlugin = compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)

fork := true
