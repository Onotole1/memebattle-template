package ru.memebattle.auth

import io.ktor.auth.BasicAuthenticationProvider
import org.springframework.security.crypto.password.PasswordEncoder
import ru.memebattle.service.UserService

class BasicAuth(private val passwordEncoder: PasswordEncoder, private val userService: UserService) {

    companion object {

        const val NAME = "jwt"
    }

    fun setup(configuration: BasicAuthenticationProvider.Configuration) {
        with(configuration) {
            validate { credentials ->
                val user = userService.getByUserName(credentials.name)

                if (passwordEncoder.matches(credentials.password, user?.password)) {
                    user
                } else {
                    null
                }
            }
        }
    }
}