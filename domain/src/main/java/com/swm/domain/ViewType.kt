package com.swm.domain

import com.swm.domain.entity.ContentVO
import java.lang.reflect.Type

enum class ViewType(
    private val viewTypeClass: Type
) {
    TITLE(ContentVO.TitleSection::class.java),
    PLUS_TITLE(ContentVO.PlusTitleSection::class.java),
    UnKnownViewType(ContentVO.UnknownContent::class.java);

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