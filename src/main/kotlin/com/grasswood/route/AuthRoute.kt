package com.grasswood.route

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.mindrot.jbcrypt.BCrypt
import com.grasswood.utils.JWT
import com.grasswood.service.ProfileService

// Declares a LoginRegister data class.
data class LoginRegister(val email: String, val password: String)

/**
 * In the Routing class of io.ktor.server.routing,
 * declare an extension function named `authRoute`.
 */
fun Routing.authRoute() {
    val profileService = ProfileService()
    route("auth") {
        post("register") {
            val credentials = call.receive<LoginRegister>()
            println(credentials)
            val salt = BCrypt.gensalt()
            println(salt)
            val hash = BCrypt.hashpw(credentials.password, salt)
            println(hash)
            // Execute profileService.registerProfile function.
            profileService.registerProfile(credentials.email, hash)
            call.respond("Success!")
        }
        post("login") {
            val credentials = call.receive<LoginRegister>()
            // Execute profileService.getProfileByEmail function.
            val profile = profileService.getProfileByEmail(credentials.email)
            if (profile == null || !BCrypt.checkpw(credentials.password, profile.password)) {
                error("Invalid Credentials")
            }
            val token = JWT.createJwtToken(profile.email)
            println(token)
            call.respond(hashMapOf("token" to token))
        }
    }
}
