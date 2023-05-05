package big.plugins

import io.ktor.serialization.jackson.*
import com.fasterxml.jackson.databind.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureSerialization`.
 */
fun Application.configureSerialization() {
    // Installs a plugin into this pipeline, if it is not yet installed.
    // FYI: https://ktor.io/docs/serialization.html
    install(ContentNegotiation) {
        // Registers the application/json content type to the ContentNegotiation plugin using Jackson.
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
