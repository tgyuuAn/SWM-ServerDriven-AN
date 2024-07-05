package com.swm.data.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.data.network.dto.ScreenDTO
import com.swm.data.network.dto.SectionDTO
import com.swm.data.network.dto.ViewTypeDTO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideSectionTypeAdapter(): TypeAdapter<SectionDTO> = object : TypeAdapter<SectionDTO>() {
        override fun write(jsonWriter: JsonWriter?, value: SectionDTO?) {
            // write 함수는 불필요함
        }

        override fun read(jsonReader: JsonReader?): SectionDTO {
            if (jsonReader == null) {
                throw IllegalArgumentException("JsonReader cannot be null")
            }

            var type = ""
            var section: SectionDTO? = null
            val jsonElement = JsonParser.parseReader(jsonReader).asJsonObject

            if (jsonElement.has("type")) {
                type = jsonElement.get("type").asString
            }

            val viewType = ViewTypeDTO.findViewTypeClassByItsName(type)
            section = Gson().fromJson(jsonElement, viewType)

            return section!!
        }
    }

    @Singleton
    @Provides
    fun provideGson(sectionTypeAdapter: TypeAdapter<SectionDTO>): Gson = GsonBuilder()
        .registerTypeAdapter(SectionDTO::class.java, sectionTypeAdapter)
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): ServerDrivenApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServerDrivenApi::class.java)

    private const val BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/"
}

interface ServerDrivenApi {
    @GET("api.swm-mobile.org/server-driven-viewtype.json")
    suspend fun getScreen(): Response<ScreenDTO>
}