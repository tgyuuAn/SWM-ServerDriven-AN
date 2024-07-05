package com.swm.data.network.dto

import com.swm.domain.model.Badge
import com.swm.domain.model.Content
import com.swm.domain.model.ImageStyle
import com.swm.domain.model.Screen
import com.swm.domain.model.Section
import com.swm.domain.model.TextStyle
import com.swm.domain.model.ViewType

data class ScreenDTO(
    val screenName: String,
    val contents: List<ContentDTO>,
) {
    fun toEntity() = Screen(
        screenName = screenName,
        contents = contents.map { it.toEntity() },
    )
}

data class ContentDTO(
    val id: String,
    val sectionComponentType: String,
    val section: SectionDTO,
) {
    fun toEntity() = Content(
        id = id,
        sectionComponentType = sectionComponentType,
        section = section.toEntity(),
    )
}

sealed class SectionDTO {
    abstract fun toEntity(): Section

    data class TitleSectionDTO(
        val type: String,
        val title: String,
        val badges: List<BadgeDTO>,
        val description: String,
    ) : SectionDTO() {
        override fun toEntity() = Section.TitleSection(
            type = ViewType.findClassByItsName(type),
            title = title,
            badges = badges.map { it.toEntity() },
            description = description,
        )
    }

    data class PlusTitleSectionDTO(
        val type: String,
        val firstRowImage: ImageStyleDTO,
        val titleText: TextStyleDTO,
        val badges: List<BadgeDTO>,
        val description: String,
    ) : SectionDTO() {
        override fun toEntity() = Section.PlusTitleSection(
            type = ViewType.findClassByItsName(type),
            firstRowImage = firstRowImage.toEntity(),
            titleText = titleText.toEntity(),
            badges = badges.map { it.toEntity() },
            description = description,
        )
    }

    data class UnKnownSectionDTO(
        val type: String,
        val description: String,
    ) : SectionDTO() {
        override fun toEntity() = Section.UnKnownSection(
            type = ViewType.findClassByItsName(type),
            description = description,
        )
    }
}

data class BadgeDTO(
    val badgeImage: String,
    val text: String,
) {
    fun toEntity() = Badge(
        badgeImage = badgeImage,
        text = text,
    )
}

data class ImageStyleDTO(
    val imgUrl: String,
    val width: String,
    val height: String,
) {
    fun toEntity() = ImageStyle(
        imgUrl = imgUrl,
        width = width.toInt(),
        height = height.toInt(),
    )
}

data class TextStyleDTO(
    val text: String,
    val textSize: String,
    val textColor: String,
    val textStyle: String,
) {
    fun toEntity() = TextStyle(
        text = text,
        textSize = textSize.toInt(),
        textColor = textColor,
        textStyle = textStyle,
    )
}