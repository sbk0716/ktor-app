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
    kotlin("jvm") version "1.8.21"
    id("io.ktor.plugin") version "2.3.0"
    id("org.flywaydb.flyway") version "8.5.4"
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
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
/**
 * public fun Project.repositories(configuration: RepositoryHandler.() -> Unit): Unit
 * Configures the repositories for this project.
 * Executes the given configuration block against the RepositoryHandler for this project.
 */
repositories {
    mavenCentral()
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
    implementation("com.zaxxer:HikariCP:2.7.8")

    // database migration
    implementation("org.flywaydb:flyway-core:6.5.2")

    // password hashing
    implementation("org.mindrot:jbcrypt:0.4")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")

}

flyway {
    url = System.getenv("DB_URL")
    user = System.getenv("DB_USER")
    password = System.getenv("DB_PASSWORD")
    baselineOnMigrate = true
}