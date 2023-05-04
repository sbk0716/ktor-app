package big

import io.ktor.server.application.*
import big.plugins.*
import my_kotlin_app.DatabaseFactory

/**
 * Declares main function.
 * This is because the entry point of the application is set to this main function.
 */
fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `mainModule`.
 */
@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.mainModule() {
    configureSerialization()
    configureSecurity()
    DatabaseFactory.init()
    configureRouting()
}
