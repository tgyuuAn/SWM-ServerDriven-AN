package com.swm.data.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.data.network.dto.ContentDTO
import com.swm.data.network.dto.ScreenDTO
import com.swm.data.network.dto.SectionDTO
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
    fun provideContentTypeAdapter(): TypeAdapter<ContentDTO> = object : TypeAdapter<ContentDTO>() {
        override fun write(jsonWriter: JsonWriter?, value: ContentDTO?) {
            // write 함수는 불필요함
        }

        override fun read(jsonReader: JsonReader?): ContentDTO {
            if (jsonReader == null) {
                throw IllegalArgumentException("JsonReader cannot be null")
            }

            var id = ""
            var sectionComponentType = ""
            var section: SectionDTO? = null

            jsonReader.beginObject()
            while (jsonReader.hasNext()) {
                when (jsonReader.nextName()) {
                    "id" -> id = jsonReader.nextString()
                    "sectionComponentType" -> sectionComponentType = jsonReader.nextString()
                    "section" -> {
                        val jsonElement = JsonParser.parseReader(jsonReader)
                        section = parseSection(sectionComponentType, jsonElement)
                    }
                }
            }
            jsonReader.endObject()

            return ContentDTO(id, sectionComponentType, section!!)
        }

        private fun parseSection(type: String, jsonElement: JsonElement): SectionDTO {
            val gson = Gson()
            return when (type) {
                "TITLE" -> gson.fromJson(jsonElement, SectionDTO.TitleSectionDTO::class.java)
                "PLUS_TITLE" -> gson.fromJson(
                    jsonElement,
                    SectionDTO.PlusTitleSectionDTO::class.java
                )

                else -> throw IllegalArgumentException("Unknown section type: $type")
            }
        }
    }

    @Singleton
    @Provides
    fun provideGson(contentTypeAdapter: TypeAdapter<ContentDTO>): Gson = GsonBuilder()
        .registerTypeAdapter(ContentDTO::class.java, contentTypeAdapter)
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