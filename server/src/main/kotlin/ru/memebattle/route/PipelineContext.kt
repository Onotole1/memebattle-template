package ru.memebattle.route

import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.auth.authentication
import io.ktor.util.pipeline.PipelineContext
import ru.memebattle.model.UserModel

val <T: Any> PipelineContext<T, ApplicationCall>.me
    get() = call.authentication.principal<UserModel>()