
plugins {
    kotlin("jvm")
}

jvmTarget = "1.6"

val allTestsRuntime by configurations.creating
val testCompile by configurations
testCompile.extendsFrom(allTestsRuntime)
val embeddableTestRuntime by configurations.creating {
    extendsFrom(allTestsRuntime)
}

dependencies {
    allTestsRuntime(commonDep("junit"))
    allTestsRuntime(intellijCoreDep()) { includeJars("intellij-core") }
    allTestsRuntime(intellijDep()) { includeJars("openapi", "idea", "idea_rt", "log4j") }
    testCompile(project(":kotlin-scripting-jvm-host"))
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(project(":compiler:daemon-common")) // TODO: fix import (workaround for jps build)
    embeddableTestRuntime(project(":kotlin-scripting-jvm-host-embeddable"))
    embeddableTestRuntime(project(":kotlin-test:kotlin-test-jvm"))
    embeddableTestRuntime(project(":kotlin-test:kotlin-test-junit"))
    embeddableTestRuntime(projectTests(":compiler:tests-common")) { isTransitive = false }
    embeddableTestRuntime(testSourceSet.output)
}

sourceSets {
    "main" {}
    "test" { projectDefault() }
}

projectTest(parallel = true) {
    workingDir = rootDir
}

projectTest(taskName = "embeddableTest", parallel = true) {
    workingDir = rootDir
    dependsOn(embeddableTestRuntime)
    classpath = embeddableTestRuntime
}
