package ru.memebattle

import android.app.Application
import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.memebattle.core.api.AuthApi
import ru.memebattle.core.api.NewsApi
import ru.memebattle.core.api.ProfileApi

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    sharedPreferencesModule,
                    networkModule
                )
            )
        }
    }
}

val networkModule = module {
    single {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        interceptor
    }

    single {
        OkHttpClient.Builder()
            .followSslRedirects(true)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://memebattle.herokuapp.com/api/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single { get<Retrofit>().create(AuthApi::class.java) }
    single { get<Retrofit>().create(NewsApi::class.java) }
    single { get<Retrofit>().create(ProfileApi::class.java) }
}

val sharedPreferencesModule = module {
    single { androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE) }
}

const val PREFS_TOKEN = "token"

const val ARGS_POST = "post"