package com.swm.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.domain.model.Badge
import com.swm.domain.model.Content
import com.swm.domain.model.ImageStyle
import com.swm.domain.model.Screen
import com.swm.domain.model.Section
import com.swm.domain.model.TextStyle
import com.swm.domain.model.ViewType
import org.junit.Assert
import org.junit.Test

class GsonTest {
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

    private fun provideGson(contentTypeAdapter: TypeAdapter<Content>): Gson = GsonBuilder()
        .registerTypeAdapter(Content::class.java, contentTypeAdapter)
        .create()

    @Test
    fun testGsonParsing() {
        val jsonResponse = """
        {
            "screenName":"Home",
            "contents" : [{
                    "id":"title_section",
                    "sectionComponentType":"TITLE",
                    "section": {
                        "type": "TitleSection",
                        "title": "Seamist Beach Cottage, Private Beach & Ocean Views",
                        "badges" : [
                            {"badgeImage" : "https://icon.airbnb.com/startRating.png", "text": "4.79(99 reviews)"}
                        ],
                        "description":"Bodega Bay, California, United States"
                    }
                },{
                    "id":"title_section",
                    "sectionComponentType":"PLUS_TITLE",
                    "section": {
                        "type": "PlusTitleSection",
                        "firstRowImage": {
                            "imgUrl": "https://icon.airbnb.com/airbnbPlusMembership.png",
                            "width":"40",
                            "height":"25"
                        },
                        "titleText": {
                            "text":"Rancho Tranquillo Rustic ChicTent",
                            "textSize":"16",
                            "textColor":"#FF0000",
                            "textStyle":"bold | italic | strike"    
                        },
                        "badges" : [
                            {
                                "badgeImage" : "https://icon.airbnb.com/startRating.png",
                                "text": "4.79(105 reviews)"
                            },
                            {
                                "badgeImage" : "https://icon.airbnb.com/superHost.png",
                                "text": "Superhost"
                            }
                        ],
                        "description":"San Juan Bautista, California, United States"
                    }
                }
            ]
        }
        """

//        val expectedContents = listOf(
//            Content(
//                id = "title_section",
//                sectionComponentType = "TITLE",
//                section = Section.TitleSection(
//                    type = "TitleSection",
//                    title = "Seamist Beach Cottage, Private Beach & Ocean Views",
//                    badges = listOf(
//                        Badge(
//                            "https://icon.airbnb.com/startRating.png",
//                            "4.79(99 reviews)"
//                        )
//                    ),
//                    description = "Bodega Bay, California, United States"
//                )
//            ),
//            Content(
//                id = "title_section",
//                sectionComponentType = "PLUS_TITLE",
//                section =
//                Section.PlusTitleSection(
//                    type = "PlusTitleSection",
//                    firstRowImage = ImageStyle(
//                        "https://icon.airbnb.com/airbnbPlusMembership.png",
//                        "40",
//                        "25"
//                    ),
//                    titleText = TextStyle(
//                        "Rancho Tranquillo Rustic ChicTent",
//                        "16",
//                        "#FF0000",
//                        "bold | italic | strike"
//                    ),
//                    badges = listOf(
//                        Badge("https://icon.airbnb.com/startRating.png", "4.79(105 reviews)"),
//                        Badge("https://icon.airbnb.com/superHost.png", "Superhost")
//                    ),
//                    description = "San Juan Bautista, California, United States"
//                )
//            )
//        )
        val gson = provideGson(provideSectionTypeAdapter())
        val screen = gson.fromJson(jsonResponse, Screen::class.java)

        val jsonOutput = gson.toJson(screen)
    }
}