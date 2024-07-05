package com.swm.domain.model

data class Screen(
    val screenName: String,
    val contents: List<Content>
)

data class Content(
    val id: String,
    val sectionComponentType: String,
    val section: Section
)

sealed class Section {
    data class TitleSection(
        val type: String,
        val title: String,
        val badges: List<Badge>,
        val description: String
    ) : Section()

    data class PlusTitleSection(
        val type: String,
        val firstRowImage: FirstRowImage,
        val titleText: TitleText,
        val badges: List<Badge>,
        val description: String
    ) : Section()
}

data class Badge(
    val badgeImage: String,
    val text: String
)

data class FirstRowImage(
    val imgUrl: String,
    val width: String,
    val height: String
)

data class TitleText(
    val text: String,
    val textSize: String,
    val textColor: String,
    val textStyle: String
)