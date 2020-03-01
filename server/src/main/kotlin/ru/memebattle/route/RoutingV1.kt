package ru.memebattle.route

import io.ktor.application.call
import io.ktor.auth.authenticate
import io.ktor.features.ParameterConversionException
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.receive
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.*
import ru.memebattle.auth.BasicAuth
import ru.memebattle.auth.JwtAuth
import ru.memebattle.common.dto.AuthenticationRequestDto
import ru.memebattle.common.dto.PostRequestDto
import ru.memebattle.common.dto.schdule.ScheduleDayRequestDto
import ru.memebattle.common.dto.user.UserRegisterRequestDto
import ru.memebattle.model.toDto
import ru.memebattle.common.dto.game.MemeRequest
import ru.memebattle.service.*

class RoutingV1(
    private val staticPath: String,
    private val postService: PostService,
    private val fileService: FileService,
    private val userService: UserService,
    private val scheduleService: ScheduleService,
    private val memeService: MemeService
) {
    fun setup(configuration: Routing) {
        with(configuration) {
            route("/api/v1/") {
                static("/static") {
                    files(staticPath)
                }

                route("/") {
                    post("/registration") {
                        val input = call.receive<UserRegisterRequestDto>()
                        val response = userService.register(input.username, input.password)
                        call.respond(response)
                    }

                    post("/authentication") {
                        val input = call.receive<AuthenticationRequestDto>()
                        val response = userService.authenticate(input)
                        call.respond(response)
                    }
                }

                authenticate(BasicAuth.NAME, JwtAuth.NAME) {
                    route("/me") {
                        get {
                            call.respond(requireNotNull(me).toDto())
                        }
                    }

                    route("/posts") {
                        get {
                            val response = postService.getAll()
                            call.respond(response)
                        }
                        get("/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                            val response = postService.getById(id)
                            call.respond(response)
                        }
                        post {
                            val input = call.receive<PostRequestDto>()
                            val response = postService.save(input)
                            call.respond(response)
                        }
                        delete("/{id}") {
                            val id = call.parameters["id"]?.toLongOrNull() ?: throw ParameterConversionException(
                                "id",
                                "Long"
                            )
                        }
                    }

                    route("/schedule") {
                        get {
                            val response = scheduleService.getAll()
                            call.respond(response)
                        }
                        post {
                            val input = call.receive<ScheduleDayRequestDto>()
                            val response = scheduleService.insert(input)
                            call.respond(HttpStatusCode.OK)
                        }
                    }

                    route("/game") {
                        get {
                            val response = memeService.getCurrentState()
                            call.respond(response)
                        }
                        post {
                            val input = call.receive<MemeRequest>()
                            val response = memeService.rateMeme(input.number)
                            call.respond(response)
                        }
                    }
                }

                route("/media") {
                    post {
                        val multipart = call.receiveMultipart()
                        val response = fileService.save(multipart)
                        call.respond(response)
                    }
                }
            }
        }
    }
}