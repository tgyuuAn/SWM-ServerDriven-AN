package com.swm.domain.model

import java.lang.reflect.Type

enum class ViewType(
    private val viewTypeClass: Type,
) {
    // Server Driven 연습 1차
    TITLE(Section.TitleSection::class.java),
    PLUS_TITLE(Section.PlusTitleSection::class.java),
    UnKnown(Section.UnKnownSection::class.java),

    // Server Driven > Rich Text 연습
    AViewType(ContentVO.AViewType::class.java),
    BViewType(ContentVO.BViewType::class.java),
    RichViewType(ContentVO.RichViewType::class.java),
    UnknownViewType(ContentVO.UnknownViewType::class.java);

    companion object {
        fun findClassByItsName(viewTypeString: String?): ViewType {
            entries.find { it.name == viewTypeString }?.let {
                return it
            } ?: return UnKnown
        }

        fun findViewTypeClassByItsName(viewTypeString: String?): Type {
            return findClassByItsName(viewTypeString).viewTypeClass
        }
    }
}