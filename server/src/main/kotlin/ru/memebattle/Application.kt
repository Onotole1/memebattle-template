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
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.with
import org.kodein.di.ktor.KodeinFeature
import org.kodein.di.ktor.kodein
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.memebattle.auth.BasicAuth
import ru.memebattle.auth.JwtAuth
import ru.memebattle.db.DatabaseCreator
import ru.memebattle.di.Tags
import ru.memebattle.exception.ConfigurationException
import ru.memebattle.exception.ErrorHandler
import ru.memebattle.repository.PostRepository
import ru.memebattle.repository.PostRepositoryInMemoryWithMutexImpl
import ru.memebattle.repository.UserRepository
import ru.memebattle.repository.UserRepositoryImpl
import ru.memebattle.route.RoutingV1
import ru.memebattle.service.FileService
import ru.memebattle.service.JWTTokenService
import ru.memebattle.service.PostService
import ru.memebattle.service.UserService
import ru.memebattle.sqldelight.data.UserQueries

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
        bind<Database>() with eagerSingleton {
            val path = environment.config.propertyOrNull("memebattle.database.path")?.getString()
            DatabaseCreator(path).create()
        }
        bind<UserQueries>() with eagerSingleton { instance<Database>().userQueries }
        constant(tag = Tags.UPLOAD_DIR) with (environment.config.propertyOrNull("memebattle.upload.dir")?.getString()
            ?: throw ConfigurationException("Upload dir is not specified"))
        bind<PasswordEncoder>() with eagerSingleton { BCryptPasswordEncoder() }
        bind<JWTTokenService>() with eagerSingleton { JWTTokenService() }
        bind<PostRepository>() with eagerSingleton { PostRepositoryInMemoryWithMutexImpl() }
        bind<PostService>() with eagerSingleton { PostService(instance()) }
        bind<FileService>() with eagerSingleton { FileService(instance(tag = Tags.UPLOAD_DIR)) }
        bind<UserRepository>() with eagerSingleton { UserRepositoryImpl(instance()) }
        bind<UserService>() with eagerSingleton { UserService(instance(), instance(), instance()) }
        bind<RoutingV1>() with eagerSingleton {
            RoutingV1(
                instance(tag = Tags.UPLOAD_DIR),
                instance(),
                instance(),
                instance()
            )
        }
        bind<BasicAuth>() with eagerSingleton { BasicAuth(instance(), instance()) }
        bind<JwtAuth>() with eagerSingleton { JwtAuth(instance(), instance()) }
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