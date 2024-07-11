package com.swm.data.network.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.domain.model.Content
import com.swm.domain.model.ContentItemVO
import com.swm.domain.model.ContentVO
import com.swm.domain.model.RichTextVO
import com.swm.domain.model.Screen
import com.swm.domain.model.Section
import com.swm.domain.model.ViewType
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
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val BASE_URL = "https://s3.ap-northeast-2.amazonaws.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    // 첫번째 Server Driven 과제
    @Singleton
    @Provides
    fun provideSectionTypeAdapter(): TypeAdapter<Content> = object : TypeAdapter<Content>() {
        override fun write(jsonWriter: JsonWriter?, value: Content?) {
            // write 함수는 불필요함
        }

        override fun read(jsonReader: JsonReader?): Content {
            if (jsonReader == null) {
                throw IllegalArgumentException("JsonReader cannot be null")
            }

            var type = ""
            val jsonElement = JsonParser.parseReader(jsonReader).asJsonObject

            if (jsonElement.has("sectionComponentType")) {
                type = jsonElement.get("sectionComponentType").asString
            }

            val viewType = ViewType.findViewTypeClassByItsName(type)

            val section: Section = Gson().fromJson(jsonElement.get("section"), viewType)
            val content = Content (
                id = jsonElement.get("id").asString,
                sectionComponentType = ViewType.findClassByItsName(type),
                section = section
            )

            return content
        }
    }

    @Singleton
    @Provides
    @Named("serverDrivenGson")
    fun provideGson(contentTypeAdapter: TypeAdapter<Content>): Gson = GsonBuilder()
        .registerTypeAdapter(Content::class.java, contentTypeAdapter)
        .create()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, @Named("serverDrivenGson") gson: Gson): ServerDrivenApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServerDrivenApi::class.java)

    // ------------------------------------------------------------------------------------------------------
    // 2. RichText Server Driven 과제
    // ✅ Rich Text
    @Singleton
    @Provides
    fun provideRichTextTypeAdapter(): TypeAdapter<ContentItemVO> = object : TypeAdapter<ContentItemVO>() {
        override fun write(jsonWriter: JsonWriter?, value: ContentItemVO?) {
            // write 함수는 불필요함
        }

        override fun read(jsonReader: JsonReader?): ContentItemVO {
            if (jsonReader == null) {
                throw IllegalArgumentException("JsonReader cannot be null")
            }

            var type = ""
            val jsonElement = JsonParser.parseReader(jsonReader).asJsonObject

            if (jsonElement.has("viewType")) {
                type = jsonElement.get("viewType").asString
            }

            val viewType = ViewType.findViewTypeClassByItsName(type)

            val content: ContentVO = Gson().fromJson(jsonElement.get("content"), viewType)
            val contentItem = ContentItemVO (
                viewType = ViewType.findClassByItsName(type),
                content = content
            )

            return contentItem
        }
    }

    @Singleton
    @Provides
    @Named("richTextGson")
    fun provideRichTextGson(richTextContentTypeAdapter: TypeAdapter<ContentItemVO>): Gson = GsonBuilder()
        .registerTypeAdapter(ContentItemVO::class.java, richTextContentTypeAdapter)
        .create()

    @Singleton
    @Provides
    fun provideRichTextRetrofit(okHttpClient: OkHttpClient, @Named("richTextGson") gson: Gson): ServerDrivenRichTextApi =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ServerDrivenRichTextApi::class.java)
}

interface ServerDrivenApi {
    @GET("api.swm-mobile.org/server-driven-viewtype.json")
    suspend fun getScreen(): Response<Screen>
}

// ✅ Rich Text
interface ServerDrivenRichTextApi {
    @GET("api.swm-mobile.org/richtext.json")
    suspend fun getRichTextScreen(): Response<RichTextVO>
}