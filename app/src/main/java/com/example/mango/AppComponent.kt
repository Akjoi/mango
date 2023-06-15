package com.example.mango

import android.content.Context
import com.example.mango.authorization.AuthorizationViewModel
import com.example.mango.authorization.data.AT
import com.example.mango.authorization.data.JSON
import com.example.mango.authorization.data.RT
import com.example.mango.profile.ProfileViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(authorizationViewModel: AuthorizationViewModel)
    fun inject(mainActivity: MainActivity)
    fun inject(profileViewModel: ProfileViewModel)
}

@Module(includes = [NetworkModule::class])
class AppModule(private val ctx: Context) {
    @Provides
    fun provideSecurePreferences(ctx: Context): SecurePreferences {
        return SecurePreferences(ctx)
    }

    @Provides
    fun provideContext(): Context {
        return ctx
    }
}

@Module
class NetworkModule {

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
        val client = OkHttpClient().newBuilder().addInterceptor(
            object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response = chain.run {
                    val request = request()
                        .newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Authorization", "Bearer ${preferences.getString(AT, "")}")
                        .build()

                    var response = proceed(
                        request
                    )

                    if (response.code == 401) {
                        preferences.remove(AT)

                        val jsonRefreshRequest =
                            JSONObject().put("refresh_token", preferences.getString(RT, ""))
                                .toString().toByteArray()

                        val JSONTYPE = "$JSON;".toMediaType()

                        val refreshRequest = Request.Builder()
                            .header("Accept", JSON)
                            .header("Content-Type", JSON)
                            .url("https://plannerok.ru/api/v1/users/refresh-token/")
                            .post(jsonRefreshRequest.toRequestBody(JSONTYPE))
                            .build()

                        response.close()

                        // Я пытался через proceed, но не срабатывало
                        val client = OkHttpClient()

                        val refreshResponse = client.newCall(refreshRequest).execute()

                        if (refreshResponse.isSuccessful) {
                            val jsonResponse = JSONObject(refreshResponse.body?.string().toString())


                            preferences.putString(AT, jsonResponse.getString(AT))
                            preferences.putString(RT, jsonResponse.getString(RT))

                            response = proceed(
                                request()
                                    .newBuilder()
                                    .addHeader("Accept", JSON)
                                    .addHeader("Authorization", "Bearer ${jsonResponse.getString(AT)}")
                                    .build()
                            )
                        }

                    }

                    response


                }
            }
        ).build()
        return client
    }

}
