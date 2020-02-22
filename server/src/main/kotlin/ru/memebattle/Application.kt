package ru.memebattle

import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.auth.Authentication
import io.ktor.auth.basic
import io.ktor.auth.jwt.jwt
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.gson.gson
import io.ktor.routing.Routing
import io.ktor.server.cio.EngineMain
import org.kodein.di.generic.instance
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import ru.memebattle.auth.BasicAuth
import ru.memebattle.auth.JwtAuth
import ru.memebattle.di.KodeinBuilder
import ru.memebattle.exception.ErrorHandler
import ru.memebattle.route.RoutingV1

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {
    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
            serializeNulls()
        }
    }

    install(StatusPages) {
        ErrorHandler.setup(this)
    }

    install(KodeinFeature) {
        KodeinBuilder(environment).setup(this)
    }

    install(Authentication) {
        jwt(JwtAuth.NAME) {
            val jwtAuth by kodein().instance<JwtAuth>()
            jwtAuth.setup(this)
        }

        basic(BasicAuth.NAME) {
            val basicAuth by kodein().instance<BasicAuth>()
            basicAuth.setup(this)
        }
    }

    install(Routing) {
        val routingV1 by kodein().instance<RoutingV1>()
        routingV1.setup(this)
    }
}