package com.grasswood.route

import io.ktor.server.application.call
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.html.body

/**
 * In the Routing class of io.ktor.server.routing,
 * declare an extension function named `healthRoute`.
 */
fun Routing.healthRoute() {
  get("") {
    call.respondHtml {
      body {
        + "OK"
      }
    }
  }
  get("health") {
    call.respondText("REST API health check succeeded!!")
  }
}
