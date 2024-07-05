package com.swm.data.network.dto

import java.lang.reflect.Type

enum class ViewTypeDTO(
    val viewTypeClass: Type,
) {
    TitleSection(SectionDTO.TitleSectionDTO::class.java),
    PlusTitleSection(SectionDTO.PlusTitleSectionDTO::class.java),
    UnKnown(SectionDTO.UnKnownSectionDTO::class.java);

    companion object {
        fun findClassByItsName(viewTypeString: String?): ViewTypeDTO {
            entries.find { it.name == viewTypeString }?.let {
                return it
            } ?: return UnKnown
        }

        fun findViewTypeClassByItsName(viewTypeString: String?): Type {
            return findClassByItsName(viewTypeString).viewTypeClass
        }
    }
}