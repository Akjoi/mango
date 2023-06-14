package com.example.mango

import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.confirmcode.ConfirmCodeViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(authorizationViewModel: AuthorizationViewModel)
    fun inject(confirmCodeViewModel: ConfirmCodeViewModel)
}

@Module(includes = [NetworkModule::class])
class AppModule {}

@Module
class NetworkModule {


//    Лучше брать Base URL из ресурсов
//    И на реальном проекте я бы укоротил его до https://plannerok.ru/api
//    Поскольку там и версии могут меняться и остальной путь. Но в тестовом всё идёт на users

    @Provides
    fun provideApi(
        okHttpClient: OkHttpClient, gsonConverterFactory: GsonConverterFactory
    ): Api {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://plannerok.ru/api/v1/users/")
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
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient().newBuilder().build()
    }
}

//@Module
//interface AppBindsModule {
//
//    @Binds
//    fun bindPlayerRepository(playerRepository: PlayerRepository): IPlayerRepository
//}