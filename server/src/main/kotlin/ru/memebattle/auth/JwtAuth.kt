package ru.memebattle.auth

import io.ktor.auth.jwt.JWTAuthenticationProvider
import ru.memebattle.service.JWTTokenService
import ru.memebattle.service.UserService

class JwtAuth(
    private val jwtService: JWTTokenService,
    private val userService: UserService
) {

    companion object {

        const val NAME = "basic"
    }

    fun setup(configuration: JWTAuthenticationProvider.Configuration) {
        with(configuration) {
            verifier(jwtService.verifier)

            validate {
                val id = it.payload.getClaim("id").asLong()
                userService.getModelById(id)
            }
        }
    }
}