package big.plugins

import big.utils.JWT
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import big.service.ProfileService
import org.mindrot.jbcrypt.BCrypt

// Declares a LoginRegister data class.
data class LoginRegister(val email: String, val password: String)

/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureRouting`.
 */
fun Application.configureRouting() {
    val profileService = ProfileService()
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/health") {
            call.respondText("OK!")
        }
        post("/register") {
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
        post("/login") {
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

        // Creates a route that allows you to define authorization scope for application resources.
        authenticate {
            get("/profiles") {
                // Execute profileService.getAllUsers function.
                val users = profileService.getAllUsers()
                call.respond(users)
            }
        }
    }
}
