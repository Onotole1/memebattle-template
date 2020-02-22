package ru.memebattle.di

import io.ktor.application.ApplicationEnvironment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import org.kodein.di.generic.instance
import org.kodein.di.generic.with
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import ru.memebattle.Database
import ru.memebattle.auth.BasicAuth
import ru.memebattle.auth.JwtAuth
import ru.memebattle.db.DatabaseCreator
import ru.memebattle.exception.ConfigurationException
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

class KodeinBuilder(private val environment: ApplicationEnvironment) {

    companion object {
        private const val UPLOAD_DIR = "upload-dir"
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(builder: Kodein.MainBuilder) {
        with(builder) {
            bind<Database>() with eagerSingleton {
                val path = environment.config.propertyOrNull("memebattle.database.path")?.getString()
                DatabaseCreator(path).create()
            }
            bind<UserQueries>() with eagerSingleton { instance<Database>().userQueries }
            constant(tag = UPLOAD_DIR) with (environment.config.propertyOrNull("memebattle.upload.dir")?.getString()
                ?: throw ConfigurationException("Upload dir is not specified"))
            bind<PasswordEncoder>() with eagerSingleton { BCryptPasswordEncoder() }
            bind<JWTTokenService>() with eagerSingleton { JWTTokenService() }
            bind<PostRepository>() with eagerSingleton { PostRepositoryInMemoryWithMutexImpl() }
            bind<PostService>() with eagerSingleton { PostService(instance()) }
            bind<FileService>() with eagerSingleton { FileService(instance(tag = UPLOAD_DIR)) }
            bind<UserRepository>() with eagerSingleton { UserRepositoryImpl(instance()) }
            bind<UserService>() with eagerSingleton { UserService(instance(), instance(), instance()) }
            bind<RoutingV1>() with eagerSingleton {
                RoutingV1(
                    instance(tag = UPLOAD_DIR),
                    instance(),
                    instance(),
                    instance()
                )
            }
            bind<BasicAuth>() with eagerSingleton { BasicAuth(instance(), instance()) }
            bind<JwtAuth>() with eagerSingleton { JwtAuth(instance(), instance()) }
        }
    }
}