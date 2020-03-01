package ru.memebattle.di

import io.ktor.application.ApplicationEnvironment
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.eagerSingleton
import ru.memebattle.route.RoutingV1

class KodeinBuilder(private val environment: ApplicationEnvironment) {

    companion object {
        private const val UPLOAD_DIR = "upload-dir"
    }

    @Suppress("EXPERIMENTAL_API_USAGE")
    fun setup(builder: Kodein.MainBuilder) {
        with(builder) {

            bind<RoutingV1>() with eagerSingleton {
                RoutingV1()
            }
        }
    }
}