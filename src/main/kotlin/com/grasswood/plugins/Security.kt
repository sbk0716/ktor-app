package com.grasswood.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.application.*
import com.grasswood.utils.JWT


/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureSecurity`.
 */
fun Application.configureSecurity() {
    // Installs the Authentication plugin if not yet installed and invokes block on its config.
    // FYI: https://ktor.io/docs/jwt.html
    authentication {
        // Installs the JWT Authentication provider.
        jwt(name = "auth-jwt") {
            // Specifies a JWT realm to be passed in WWW-Authenticate header.
            realm = JWT.jwtRealm
            // Provides a JWTVerifier used to verify a token format and signature.
            verifier(verifier = JWT.jwtVerifier)
            // Allows you to perform additional validations on the JWT payload.
            validate { credential ->
                val emailFromClaim = credential.payload.getClaim("email").asString()
                UserIdPrincipal(emailFromClaim)
            }
        }
    }

}
