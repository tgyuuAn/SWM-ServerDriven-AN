package com.swm.domain.model

import java.lang.reflect.Type

enum class ViewType(
    private val viewTypeClass: Type,
) {
    TitleSection(Section.TitleSection::class.java),
    PlusTitleSection(Section.PlusTitleSection::class.java),
    UnKnown(Section.UnKnownSection::class.java);

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