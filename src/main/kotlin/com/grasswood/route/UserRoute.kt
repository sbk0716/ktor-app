package com.grasswood.route

import io.ktor.server.application.call
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.grasswood.service.ProfileService

/**
 * In the Routing class of io.ktor.server.routing,
 * declare an extension function named `userRoute`.
 */
fun Routing.userRoute() {
    val profileService = ProfileService()
    // Creates a route that allows you to define authorization scope for application resources.
    authenticate("auth-jwt") {
        route("users") {
            get("profiles") {
                // Execute profileService.getAllUsers function.
                val users = profileService.getAllUsers()
                call.respond(users)
           }
        }
    }
}
