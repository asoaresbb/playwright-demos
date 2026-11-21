plugins {
    kotlin("jvm") version "2.2.20"
}

group = "asoares.testing"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("com.microsoft.playwright:playwright:1.65.1")
}

// "gradle test" runs the tests in Chromium, Firefox and WebKit, like the TypeScript and Python folders.
// Each browser has its own task (testChromium, testFirefox, testWebkit), which sets the "browser" property read by BrowserOptions.
val browserTests = listOf("chromium", "firefox", "webkit").map { browser ->
    tasks.register<Test>("test${browser.replaceFirstChar { it.uppercase() }}") {
        description = "Runs the tests in $browser."
        group = "verification"
        testClassesDirs = sourceSets["test"].output.classesDirs
        classpath = sourceSets["test"].runtimeClasspath
        useJUnitPlatform()
        systemProperty("browser", browser)
    }
}

tasks.test {
    // The browser tasks run the tests; this task only groups them.
    // useJUnitPlatform() stays here so the Kotlin plugin picks the JUnit 5 engine for kotlin("test").
    useJUnitPlatform()
    enabled = false
    dependsOn(browserTests)
}

kotlin {
    jvmToolchain(21)
}

tasks.register<JavaExec>("playwright") {
    classpath = sourceSets["test"].runtimeClasspath
    mainClass.set("com.microsoft.playwright.CLI")
}
