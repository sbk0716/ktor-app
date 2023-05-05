package com.grasswood.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import com.grasswood.route.healthRoute
import com.grasswood.route.authRoute
import com.grasswood.route.userRoute


/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureRouting`.
 */
fun Application.configureRouting() {
    // Installs a Routing plugin for this Application and runs a configuration script on it.
    routing {
        healthRoute()
        authRoute()
        userRoute()
    }
}
