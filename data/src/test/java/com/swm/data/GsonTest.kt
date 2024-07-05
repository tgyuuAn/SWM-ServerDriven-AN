package com.swm.data

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import com.swm.data.network.dto.BadgeDTO
import com.swm.data.network.dto.ContentDTO
import com.swm.data.network.dto.ImageStyleDTO
import com.swm.data.network.dto.ScreenDTO
import com.swm.data.network.dto.SectionDTO
import com.swm.data.network.dto.TextStyleDTO
import com.swm.data.network.dto.ViewTypeDTO
import org.junit.Assert
import org.junit.Test

class GsonTest {
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

    private fun provideGson(sectionTypeAdapter: TypeAdapter<SectionDTO>): Gson = GsonBuilder()
        .registerTypeAdapter(SectionDTO::class.java, sectionTypeAdapter)
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

        val gson = provideGson(provideSectionTypeAdapter())
        val parsedResponse = gson.fromJson(jsonResponse, ScreenDTO::class.java)

        Assert.assertEquals("Home", parsedResponse.screenName)
        Assert.assertEquals(expectedContents, parsedResponse.contents)
    }
}