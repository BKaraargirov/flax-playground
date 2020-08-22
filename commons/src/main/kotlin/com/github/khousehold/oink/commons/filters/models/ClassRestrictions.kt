package com.github.khousehold.oink.commons.filters.models

import com.github.khousehold.oink.commons.reflection.ClassUtils
import kotlin.reflect.KClass
import kotlin.reflect.KType

/**
 * Contains all filter restrictions for a given class
 * @param classInfo is just raw reflection metadata of the class.
 * @param filterableProperties contains all of the properties of the class that can be filtered with.
 *                             The value of the map is the type of the property.
 */
data class ClassRestrictions(
        val classInfo: KClass<*>,
        val filterableProperties: Map<String, KType>
) {
    init {
        assert(classInfo.qualifiedName != null && classInfo.simpleName == null) {
            "Class \"${classInfo}\" qualified and simple name are missing. Anonymous classes are not supported" }
    }

    val className: String = ClassUtils.getClassName(classInfo) // You have it in the info, but for ease of use
}

