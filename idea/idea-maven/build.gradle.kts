plugins {
    kotlin("jvm")
    id("jps-compatible")
}

dependencies {
    compile(project(":core:util.runtime"))
    compile(project(":compiler:frontend"))
    compile(project(":compiler:frontend.java"))
    compile(project(":compiler:util"))
    compile(project(":compiler:cli-common"))
    compile(project(":kotlin-build-common"))

    compile(project(":js:js.frontend"))

    compile(project(":idea"))
    compile(project(":idea:idea-jvm"))
    compile(project(":idea:idea-jps-common"))

    compileOnly(intellijDep())
    compileOnly(intellijPluginDep("java"))
    testCompileOnly(intellijPluginDep("java"))
    testRuntimeOnly(intellijPluginDep("java"))
    testRuntimeOnly(intellijPluginDep("java-ide-customization"))

    excludeInAndroidStudio(rootProject) { compileOnly(intellijPluginDep("maven")) }

    excludeInAndroidStudio(rootProject) {
        compileOnly(intellijPluginDep("maven-model"))
    }

    testCompile(projectTests(":idea"))
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(projectTests(":idea:idea-test-framework"))

    testCompileOnly(intellijDep())
    if (Ide.IJ()) {
        testCompileOnly(intellijPluginDep("maven"))
        testRuntime(intellijPluginDep("maven"))

        testCompileOnly(intellijPluginDep("maven-model"))
        testRuntime(intellijPluginDep("maven-model"))
        testRuntime(intellijPluginDep("repository-search"))
    }

    testCompile(project(":idea:idea-native")) { isTransitive = false }
    testRuntime(project(":native:frontend.native"))

    testRuntimeOnly(toolsJar())
    testRuntime(project(":kotlin-reflect"))
    testRuntime(project(":idea:idea-jvm"))
    testRuntime(project(":idea:idea-android"))
    testRuntime(project(":plugins:android-extensions-ide"))
    testRuntime(project(":plugins:lint"))
    testRuntime(project(":sam-with-receiver-ide-plugin"))
    testRuntime(project(":allopen-ide-plugin"))
    testRuntime(project(":noarg-ide-plugin"))
    testRuntime(project(":plugins:parcelize:parcelize-ide"))
    testRuntime(project(":kotlin-scripting-idea"))
    testRuntime(project(":kotlinx-serialization-ide-plugin"))
    testRuntime(project(":plugins:lombok:lombok-ide-plugin"))

    testRuntime(intellijDep())
    // TODO: the order of the plugins matters here, consider avoiding order-dependency
    testRuntime(intellijPluginDep("junit"))
    testRuntime(intellijPluginDep("testng"))
    testRuntime(intellijPluginDep("properties"))
    testRuntime(intellijPluginDep("gradle"))
    testRuntime(intellijPluginDep("Groovy"))
    testRuntime(intellijPluginDep("coverage"))
    testRuntime(intellijPluginDep("android"))
    testRuntime(intellijPluginDep("smali"))

    if (Ide.AS()) {
        testRuntime(intellijPluginDep("android-layoutlib"))
        testRuntime(intellijPluginDep("platform-images"))
    }
}

if (Ide.IJ()) {
    sourceSets {
        "main" { projectDefault() }
        "test" { projectDefault() }
    }
} else {
    sourceSets {
        "main" { }
        "test" { }
    }
}

testsJar()

projectTest(parallel = true) {
    workingDir = rootDir
}

if (Ide.IJ()) {
    runtimeJar()
}
