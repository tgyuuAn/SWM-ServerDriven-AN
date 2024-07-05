package com.swm.domain

import com.swm.domain.model.Section
import java.lang.reflect.Type

enum class ViewType(
    private val viewTypeClass: Type
) {
    TITLE(Section.TitleSection::class.java),
    PLUS_TITLE(Section.PlusTitleSection::class.java),
    UnKnownViewType(Section.UnKnownSection::class.java);

    // JSON에서 넘겨주는 sectionComponentType의 이름으로
    companion object {
        fun findClassByItsName(viewTypeString: String?): ViewType {
            values().find { it.name == viewTypeString }?.let {
                return it
            } ?: return UnKnownViewType
        }

        fun findViewTypeClassByItsName(viewTypeString: String?): Type {
            return findClassByItsName(viewTypeString).viewTypeClass
        }
    }
}