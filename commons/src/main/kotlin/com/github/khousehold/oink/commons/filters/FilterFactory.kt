package com.github.khousehold.oink.commons.filters

import com.github.khousehold.oink.commons.filters.models.IFilter
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.KType

interface FilterFactory<T> {
  fun transformFilters(filters: IFilter, kType: KClass<*>): T

  fun transformFilters(filters: IFilter, type: Class<*>): T {
    val kType = Reflection.createKotlinClass(type)

    return this.transformFilters(filters, kType)
  }
}