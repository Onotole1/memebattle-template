package ru.memebattle

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.memebattle.core.api.*
import ru.memebattle.core.utils.getString

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
        OkHttpClient.Builder()
            .followSslRedirects(true)
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(
                object : Interceptor {
                    override fun intercept(chain: Interceptor.Chain): Response {
                        val original = chain.request()
                        val token = get<SharedPreferences>().getString(PREFS_TOKEN)
                        return if (token != null) {
                            val requestBuilder = original.newBuilder()
                                .addHeader("Authorization", "Bearer $token")
                            val request = requestBuilder.build()
                            chain.proceed(request)
                        } else {
                            chain.proceed(original)
                        }
                    }
                }
            )
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
    single { get<Retrofit>().create(TimetableApi::class.java) }
    single { get<Retrofit>().create(ReportApi::class.java) }
    single { get<Retrofit>().create(GameApi::class.java) }
}

val sharedPreferencesModule = module {
    single { androidContext().getSharedPreferences("settings", Context.MODE_PRIVATE) }
}

const val PREFS_TOKEN = "token"
const val PREFS_TIMETABLE = "timetable"
const val PREFS_ALARM = "alarm"
const val PREFS_ALARM_HOUR = "hour"
const val PREFS_ALARM_MINUTE = "minute"

const val ARGS_POST = "post"
const val ARGS_SEMESTER = "semester"