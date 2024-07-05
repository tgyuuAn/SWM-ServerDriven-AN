package com.swm.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.data.network.model.BadgeDTO
import com.swm.data.network.model.ContentDTO
import com.swm.data.network.model.ImageStyleDTO
import com.swm.data.network.model.ScreenDTO
import com.swm.data.network.model.SectionDTO
import com.swm.data.network.model.TextStyleDTO
import org.junit.Assert
import org.junit.Test

class GsonTest {
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

    private fun provideGson(contentTypeAdapter: TypeAdapter<ContentDTO>): Gson = GsonBuilder()
        .registerTypeAdapter(ContentDTO::class.java, contentTypeAdapter)
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

        val expectedContents = listOf(
            ContentDTO(
                id = "title_section",
                sectionComponentType = "TITLE",
                section = SectionDTO.TitleSectionDTO(
                    type = "TitleSection",
                    title = "Seamist Beach Cottage, Private Beach & Ocean Views",
                    badges = listOf(
                        BadgeDTO(
                            "https://icon.airbnb.com/startRating.png",
                            "4.79(99 reviews)"
                        )
                    ),
                    description = "Bodega Bay, California, United States"
                )
            ),
            ContentDTO(
                id = "title_section",
                sectionComponentType = "PLUS_TITLE",
                section =
                SectionDTO.PlusTitleSectionDTO(
                    type = "PlusTitleSection",
                    firstRowImage = ImageStyleDTO(
                        "https://icon.airbnb.com/airbnbPlusMembership.png",
                        "40",
                        "25"
                    ),
                    titleText = TextStyleDTO(
                        "Rancho Tranquillo Rustic ChicTent",
                        "16",
                        "#FF0000",
                        "bold | italic | strike"
                    ),
                    badges = listOf(
                        BadgeDTO("https://icon.airbnb.com/startRating.png", "4.79(105 reviews)"),
                        BadgeDTO("https://icon.airbnb.com/superHost.png", "Superhost")
                    ),
                    description = "San Juan Bautista, California, United States"
                )
            )
        )

        val gson = provideGson(provideContentTypeAdapter())
        val parsedResponse = gson.fromJson(jsonResponse, ScreenDTO::class.java)

        Assert.assertEquals("Home", parsedResponse.screenName)
        Assert.assertEquals(expectedContents, parsedResponse.contents)
    }
}