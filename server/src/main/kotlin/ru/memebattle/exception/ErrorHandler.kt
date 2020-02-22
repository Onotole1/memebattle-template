package ru.memebattle.exception

import io.ktor.application.call
import io.ktor.features.ParameterConversionException
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.response.respond

object ErrorHandler {

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(configuration: StatusPages.Configuration) {
        with(configuration) {
            exception<NotImplementedError> {
                call.respond(HttpStatusCode.NotImplemented)
            }
            exception<ParameterConversionException> {
                call.respond(HttpStatusCode.BadRequest)
            }
            exception<UserExistsException> {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}