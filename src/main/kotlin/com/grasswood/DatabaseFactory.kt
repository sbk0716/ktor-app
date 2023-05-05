package big

import com.typesafe.config.ConfigFactory
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * DatabaseFactory
 */
object DatabaseFactory {
    // Creates Config instance.
    private val currentConfig = ConfigFactory.load()
    // Implements ApplicationConfig by loading configuration from HOCON data structures.
    private val appConfig = HoconApplicationConfig(currentConfig)
    private val dbUrl = appConfig.property("db.jdbcUrl").getString()
    private val dbUser = appConfig.property("db.dbUser").getString()
    private val dbPassword = appConfig.property("db.dbPassword").getString()

    /**
     * Create a HikariDataSource with the specified configuration.
     * Retrieves a Database instance by simple providing connection parameters.
     * Sets Fluent configuration for Flyway.
     * Creates the new fully-configured Flyway instance.
     * Starts the database migration with the new fully-configured Flyway instance.
     */
    fun init() {
        // Executes hikari function to create a HikariDataSource with the specified configuration.
        val dataSource = hikari()
        // Retrieves a Database instance by simple providing connection parameters.
        val db = Database.connect(dataSource)
        println(db)
        // Sets Fluent configuration for Flyway.
        val flywayConfig = Flyway.configure()
        // Creates the new fully-configured Flyway instance.
        val flyway = flywayConfig.dataSource(dbUrl, dbUser, dbPassword).load()
        // Starts the database migration with the new fully-configured Flyway instance.
        flyway.migrate()
    }

    /**
     * Creates a HikariDataSource with the specified configuration.
     */
    private fun hikari(): HikariDataSource {
        // Creates HikariConfig() instance.
        val config = HikariConfig()
        // Add currentSchema.
        config.addDataSourceProperty("currentSchema", "private");
        config.driverClassName = "org.postgresql.Driver"
        config.jdbcUrl = dbUrl
        config.username = dbUser
        config.password = dbPassword
        config.maximumPoolSize = 3
        config.isAutoCommit = false
        config.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        config.validate()
        // Creates a HikariDataSource with the specified configuration.
        val hikariDataSource = HikariDataSource(config)
        println(hikariDataSource)
        return hikariDataSource
    }

    suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }
}

