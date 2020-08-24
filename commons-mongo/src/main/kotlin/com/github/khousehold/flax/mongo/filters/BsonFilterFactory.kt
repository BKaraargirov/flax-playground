package com.github.khousehold.flax.mongo.filters

import com.github.khousehold.oink.commons.errors.ErrorHandlingUtils
import com.github.khousehold.oink.commons.filters.FilterFactory
import com.github.khousehold.oink.commons.filters.FilterValidator
import com.github.khousehold.oink.commons.filters.errors.FilterValidationErrorFactory
import com.github.khousehold.oink.commons.filters.models.*
import com.github.khousehold.oink.commons.reflection.ClassUtils
import com.mongodb.client.model.Filters
import org.bson.conversions.Bson
import kotlin.reflect.KClass

class BsonFilterFactory(
  private val filterValidator: FilterValidator,
  private val classUtils: ClassUtils
) : FilterFactory<Bson> {
  override fun transformFilters(
      filters: IFilter, targetClass: KClass<*>
  ): Bson {
    val validations = filterValidator.validate(classUtils.getClassName(targetClass), filters)

    ErrorHandlingUtils.throwIfInvalid(validations, FilterValidationErrorFactory())

    return createDocumentFilter(filters)
  }

  fun createDocumentFilter(filter: IFilter): Bson = when (filter) {
    is LogicalFilter -> {
      when (filter.type) {
        LogicalFilterType.AND -> Filters.and(filter.filters.map { createDocumentFilter(it) })
        LogicalFilterType.OR -> Filters.or(filter.filters.map { createDocumentFilter(it) })
        LogicalFilterType.NOT -> Filters.not(createDocumentFilter(filter.filters[0]))
      }
    }
    is Filter -> transformFilter(filter)
    else -> throw UnknownError()
  }

  /**
   * Transform a single custom filter into a MongoDB filter
   */
  fun transformFilter(filter: Filter): Bson {
    return when (filter.operation) {
      FilterOperation.Equal -> Filters.eq(filter.propertyName, filter.value)

      FilterOperation.Contains -> Filters.regex(filter.propertyName, ".*" + filter.value + ".*")

      FilterOperation.GreaterThan -> Filters.gt(filter.propertyName, filter.value)

      FilterOperation.GreaterThanEq -> Filters.gte(filter.propertyName, filter.value)

      FilterOperation.LowerThan -> Filters.lt(filter.propertyName, filter.value)

      FilterOperation.LowerThanEq -> Filters.lte(filter.propertyName, filter.value)
    }
  }
}