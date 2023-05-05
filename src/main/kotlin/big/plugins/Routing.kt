package big.plugins

import big.route.healthRoute
import big.route.authRoute
import big.route.userRoute
import io.ktor.server.routing.*
import io.ktor.server.application.*


/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureRouting`.
 */
fun Application.configureRouting() {
    // Installs a Routing plugin for the this Application and runs a configuration script on it.
    routing {
        healthRoute()
        authRoute()
        userRoute()
    }
}
