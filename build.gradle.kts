// Gradle properties.
// https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project
val postgresql_version: String by project

/**
 * public final fun plugins(block: PluginDependenciesSpecScope.() -> Unit): Unit
 * Configures the plugin dependencies for this project.
 */
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    // https://docs.gradle.org/current/userguide/application_plugin.html
    application
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    // https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "1.8.21"
    // Provides the ability to package and containerize your Ktor application.
    // https://plugins.gradle.org/plugin/io.ktor.plugin
    id("io.ktor.plugin") version "2.3.0"
    // Flyway is an open-source database migration tool.
    // https://plugins.gradle.org/plugin/org.flywaydb.flyway
    id("org.flywaydb.flyway") version "9.17.0"
}

group = "big"
version = "0.0.1"

/**
 * public fun Project.application(configure: Action<JavaApplication>): Unit
 * Configures the application extension.
 */
application {
    mainClass.set("big.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    // Set array of string arguments to pass to the JVM when running the application.
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
/**
 * public fun Project.repositories(configuration: RepositoryHandler.() -> Unit): Unit
 * Configures the repositories for this project.
 * Executes the given configuration block against the RepositoryHandler for this project.
 */
repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    // Use Early Access Program (EAP) for resolving dependencies.
    maven {
        url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
    }
}

/**
 * public fun Project.dependencies(configuration: DependencyHandlerScope.() -> Unit): Unit
 * Configures the dependencies for this project.
 * Executes the given configuration block against the DependencyHandlerScope for this project.
 */
dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")

    // connect to database
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.postgresql:postgresql:$postgresql_version")

    // database pooling
    implementation("com.zaxxer:HikariCP:5.0.1")

    // database migration
    implementation("org.flywaydb:flyway-core:9.17.0")

    // password hashing
    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}

flyway {
    createSchemas = true
    defaultSchema = "private"
    schemas = listOf("private", "tutorial").toTypedArray()
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
}