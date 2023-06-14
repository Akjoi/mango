package com.example.mango

import android.content.Context
import android.util.Log
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.authorization.data.AT
import com.example.mango.authorization.data.TAG
import com.example.mango.confirmcode.ConfirmCodeViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(authorizationViewModel: AuthorizationViewModel)
    fun inject(confirmCodeViewModel: ConfirmCodeViewModel)
}

@Module(includes = [NetworkModule::class])
class AppModule {}


@Module
class PreferencesModule(private val ctx: Context) {
    @Provides
    fun provideSecurePreferences(ctx: Context): SecurePreferences {
        return SecurePreferences(ctx)
    }

    @Provides
    fun provideContext(): Context {
        return ctx
    }

}

@Module(includes = [PreferencesModule::class])
class NetworkModule {


//    Лучше брать Base URL из ресурсов
//    И на реальном проекте я бы укоротил его до https://plannerok.ru/api
//    Поскольку там и версии могут меняться и остальной путь. Но в тестовом всё идёт на users

    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory,
    ): Api {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://plannerok.ru")
            .addConverterFactory(gsonConverterFactory)
            .build()
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun gson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    fun provideOkHttpClient(preferences: SecurePreferences): OkHttpClient {
        val client = OkHttpClient().newBuilder().build()
        client.interceptors().add(object: Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                val original: Request = chain.request()
                val request: Request = original.newBuilder()
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer ${preferences.getString(AT, "")}" )
                    .method(original.method(), original.body())
                    .build()
                Log.i(TAG, request.toString())
                return chain.proceed(request)
            }

        })
        return client
    }

}



//@Module
//interface AppBindsModule {
//
//    @Binds
//    fun bindPlayerRepository(playerRepository: PlayerRepository): IPlayerRepository
//}