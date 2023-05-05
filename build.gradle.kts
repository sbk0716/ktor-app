// Gradle properties.
// FYI: https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties
val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val exposedVersion: String by project
val postgresqlVersion: String by project

/**
 * public final fun plugins(block: PluginDependenciesSpecScope.() -> Unit): Unit
 * Configures the plugin dependencies for this project.
 */
plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    // FYI: https://docs.gradle.org/current/userguide/application_plugin.html
    application
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    // FYI: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
    kotlin("jvm") version "1.8.21"
    // Provides the ability to package and containerize your Ktor application.
    // FYI: https://plugins.gradle.org/plugin/io.ktor.plugin
    id("io.ktor.plugin") version "2.3.0"
    // Flyway is an open-source database migration tool.
    // FYI: https://plugins.gradle.org/plugin/org.flywaydb.flyway
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
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cors:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-jackson-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-auth-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-html-builder:$ktorVersion")

    // connect to database
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.postgresql:postgresql:$postgresqlVersion")

    // database pooling
    implementation("com.zaxxer:HikariCP:5.0.1")

    // database migration
    implementation("org.flywaydb:flyway-core:9.17.0")

    // password hashing
    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

/**
 * public fun Project.flyway(configure: Action<FlywayExtension>): Unit
 * Configures the flyway extension.
 * FYI: https://documentation.red-gate.com/fd/parameters-184127474.html
 */
flyway {
    // ==============================
    // Parameters > Baseline
    // ==============================
    // Whether to automatically call baseline when migrate is executed against a non-empty schema with no metadata table.
    baselineOnMigrate = true

    // ==============================
    // Parameters > Clean
    // ==============================
    // Whether to disable clean.
    cleanDisabled = false

    // ==============================
    // Parameters > Schema
    // ==============================
    // Whether Flyway should attempt to create the schemas specified in the schemas property.
    createSchemas = true
    // The default schema managed by Flyway. This schema will be the one containing the schema history table.
    defaultSchema = "private"
    // Comma-separated, case-sensitive list of schemas managed by Flyway. Flyway will attempt to create these schemas if they do not already exist, and will clean them in the order of this list.
    schemas = listOf("private", "tutorial").toTypedArray()

    // ==============================
    // Parameters > Connection
    // ==============================
    // The jdbc url to use to connect to the database.
    url = System.getenv("DB_URL")
    // The user to use to connect to the database.
    user = System.getenv("DB_USER")
    // The password to use to connect to the database
    password = System.getenv("DB_PASSWORD")
}