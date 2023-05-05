package com.grasswood.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.typesafe.config.ConfigFactory
import io.ktor.server.config.*
import java.util.*

object JWT {
    private val appConfig = HoconApplicationConfig(ConfigFactory.load())
    private val jwtSecret = appConfig.property("jwt.secret").getString()
    private val jwtIssuer = appConfig.property("jwt.issuer").getString()
    private val jwtAudience = appConfig.property("jwt.audience").getString()
    val jwtRealm = appConfig.property("jwt.realm").getString()
    private val expireMinute = appConfig.property("jwt.expireMinute").getString().toIntOrNull() ?: 1;
    private val withExpiresAt = Date(System.currentTimeMillis() + expireMinute * 60 * 1000)

    fun createJwtToken(email: String): String? {
        return JWT.create() // Returns a Json Web Token builder.
            .withAudience(jwtAudience) // Add a specific Audience ("aud") claim to the Payload.
            .withIssuer(jwtIssuer) // Add a specific Issuer ("iss") claim to the Payload.
            .withClaim("email", email) // Add a custom Claim value.
            .withExpiresAt(withExpiresAt) // Add a specific Expires At ("exp") claim to the payload.
            .sign(Algorithm.HMAC256(jwtSecret)) // Creates a new JWT and signs is with the given algorithm.
    }

    val jwtVerifier: JWTVerifier = JWT
        // Returns a Verification builder with the algorithm to be used to validate token signature.
        .require(Algorithm.HMAC256(jwtSecret))
        // Verifies whether the JWT contains an Audience ("aud") claim that contains all the values provided.
        .withAudience(jwtAudience)
        // Verifies whether the JWT contains an Issuer ("iss") claim that equals to the value provided.
        .withIssuer(jwtIssuer)
        .build()
}
