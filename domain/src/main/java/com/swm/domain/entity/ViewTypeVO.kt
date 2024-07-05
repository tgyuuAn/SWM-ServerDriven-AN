package com.swm.domain.entity

import com.swm.domain.ViewType

data class ViewTypeVO(
    val id: String,
    val sectionComponentType: ViewType,
    val section: ContentVO
)
