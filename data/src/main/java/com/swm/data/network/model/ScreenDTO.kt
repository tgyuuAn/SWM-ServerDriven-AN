package com.swm.data.network.model

import com.swm.domain.model.Badge
import com.swm.domain.model.Section
import java.time.format.TextStyle

data class ScreenDTO(
    val screenName: String,
    val contents: List<ContentDTO>,
)

data class ContentDTO(
    val id: String,
    val sectionComponentType: String,
    val section: SectionDTO,
)

sealed class SectionDTO {
    data class TitleSectionDTO(
        val type: String,
        val title: String,
        val badges: List<BadgeDTO>,
        val description: String,
    ) : SectionDTO()

    data class PlusTitleSectionDTO(
        val type: String,
        val firstRowImage: ImageStyleDTO,
        val titleText: TextStyleDTO,
        val badges: List<BadgeDTO>,
        val description: String,
    ) : SectionDTO()
}

data class BadgeDTO(
    val badgeImage: String,
    val text: String,
)

data class ImageStyleDTO(
    val imgUrl: String,
    val width: String,
    val height: String,
)

data class TextStyleDTO(
    val text: String,
    val textSize: String,
    val textColor: String,
    val textStyle: String,
)