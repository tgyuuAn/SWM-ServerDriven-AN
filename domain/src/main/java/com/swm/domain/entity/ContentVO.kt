package com.swm.domain.entity

sealed class ContentVO {
    data class TitleSection(
        val title: String,
        val badges: List<Badges>,
        val description: String,
    ) : ContentVO()

    data class PlusTitleSection(
        val firstRowImage: FirstRowImage,
        val titleText: TitleText,
        val badges: List<Badges>,
        val description: String,
    ) : ContentVO()
}

data class Badges(
    val badgeImage: String,
    val text: String
)

data class FirstRowImage(
    val imageUrl: String,
    val width: String,
    val height: String,
)

data class TitleText(
    val text: String,
    val textSize: String,
    val textColor: String,
    val textStyle: String,
)