package com.swm.domain.model

// RichText
data class RichTextVO (
    val responseData: ResponseDataVO = ResponseDataVO(),
)

data class ResponseDataVO (
    val screenName: String = "",
    val contents: List<ContentItemVO> = listOf(),
)

data class ContentItemVO(
    val viewType: ViewType,
    val content: ContentVO,
)

sealed class ContentVO {
    data class AViewType (
        val title: String,
        val iconUrl: String,
    ) : ContentVO()

    data class BViewType (
        val title: String,
    ) : ContentVO()

    data class RichViewType (
        val title: String,
        val richText: List<RichText>,
    ) : ContentVO()

    data class UnknownViewType (
        val title: String,
    ) : ContentVO()
}

data class RichText (
    val text: Text?,
    val image: Image?,
)

data class Text(
    val text: String,
    val fontSize: Int,
    val textColor: String?,
    val textStyle: List<String>?,
)

data class Image(
    val url: String,
    val width: Int,
    val height: Int,
)