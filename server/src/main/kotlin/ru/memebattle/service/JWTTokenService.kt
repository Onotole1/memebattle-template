package ru.memebattle.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm

class JWTTokenService {
    private val secret = "5c2dbef6-289c-46e6-8cfd-d8b3292d373a"
    private val algo = Algorithm.HMAC256(secret)

    val verifier: JWTVerifier = JWT.require(algo).build()

    fun generate(id: Long): String = JWT.create()
        .withClaim("id", id)
        .sign(algo)
}