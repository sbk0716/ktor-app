package com.grasswood.plugins

import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.cors.routing.CORS

/**
 * In the Application class of io.ktor.server.application,
 * declare an extension function named `configureCORS`.
 */
fun Application.configureCORS() {
    // `ktor-server-cors` that allows you to configure handling cross-origin requests.
    // FYI: https://ktor.io/docs/cors.html
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Patch)
        allowMethod(HttpMethod.Delete)
        allowHeader(HttpHeaders.Authorization)
        allowHeader(HttpHeaders.ContentType)
    }
}
