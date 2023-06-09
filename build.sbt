// https://typelevel.org/sbt-typelevel/faq.html#what-is-a-base-version-anyway
ThisBuild / tlBaseVersion := "0.0" // your current series x.y

ThisBuild / organization := "pink.cozydev"
ThisBuild / organizationName := "CozyDev"
ThisBuild / startYear := Some(2023)
ThisBuild / licenses := Seq(License.Apache2)
ThisBuild / developers := List(
  // your GitHub handle and name
  tlGitHubDev("samspills", "Sam Pillsworth")
)

// publish to s01.oss.sonatype.org (set to true to publish to oss.sonatype.org instead)
ThisBuild / tlSonatypeUseLegacyHost := false

// publish website from this branch
ThisBuild / tlSitePublishBranch := Some("main")

val Scala213 = "2.13.10"
ThisBuild / crossScalaVersions := Seq(Scala213, "3.3.0")
ThisBuild / scalaVersion := Scala213 // the default Scala

lazy val root = tlCrossRootProject.aggregate(core)

lazy val core = crossProject(JVMPlatform, JSPlatform, NativePlatform)
  .in(file("core"))
  .settings(
    name := "snakecase",
    scalacOptions := scalacOptions.value
      .filterNot(_ == "-source:3.0-migration"),
    libraryDependencies ++= Seq(
      "org.typelevel" %%% "cats-core" % "2.9.0",
      "org.typelevel" %%% "cats-parse" % "0.3.9",
      "org.typelevel" %%% "literally" % "1.1.0",
      "org.scalameta" %%% "munit" % "1.0.0-M8" % Test
    )
  )

import laika.helium.config.{IconLink, HeliumIcon}
lazy val docs = project
  .in(file("site"))
  .enablePlugins(TypelevelSitePlugin)
  .dependsOn(core.jvm)
  .settings(
    tlFatalWarningsInCi := false,
    tlSiteRelatedProjects := Seq(
      "literally" -> url("https://github.com/typelevel/literally")
    ),
    tlSiteHelium := {
      tlSiteHelium.value.site.darkMode.disabled.site
        .resetDefaults(topNavigation = true)
        .site
        .topNavigationBar(
          homeLink = IconLink.external(
            "https://github.com/cozydev-pink/snakecase",
            HeliumIcon.github
          )
        )
    }
  )
