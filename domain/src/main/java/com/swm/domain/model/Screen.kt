package com.swm.domain.model

data class Screen(
    val screenName: String = "",
    val contents: List<Content> = listOf(),
)

data class Content(
    val id: String,
    val sectionComponentType: ViewType,
    val section: Section,
)

sealed class Section {
    data class TitleSection(
        val type: String,
        val title: String,
        val badges: List<Badge>,
        val description: String,
    ) : Section()

    data class PlusTitleSection(
        val type: String,
        val firstRowImage: ImageStyle,
        val titleText: TextStyle,
        val badges: List<Badge>,
        val description: String,
    ) : Section()

    data class UnKnownSection(
        val type: String,
        val description: String,
    ) : Section()

}

data class Badge(
    val badgeImage: String,
    val text: String,
)

data class ImageStyle(
    val imgUrl: String,
    val width: String,
    val height: String,
)

data class TextStyle(
    val text: String,
    val textSize: String,
    val textColor: String,
    val textStyle: String,
)