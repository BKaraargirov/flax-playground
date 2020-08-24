package com.github.khousehold.flax.mongo.filters

import com.github.khousehold.oink.commons.errors.ErrorHandlingUtils
import com.github.khousehold.oink.commons.filters.FilterFactory
import com.github.khousehold.oink.commons.filters.FilterValidator
import com.github.khousehold.oink.commons.filters.errors.FilterValidationErrorFactory
import com.github.khousehold.oink.commons.filters.models.*
import com.github.khousehold.oink.commons.reflection.ClassUtils
import com.mongodb.client.model.Filters
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import kotlin.reflect.KClass

class QueryFilterFactory(
    private val filterValidator: FilterValidator,
    private val classUtils: ClassUtils
) : FilterFactory<Query> {
  override fun transformFilters(
      filters: IFilter, targetClass: KClass<*>
  ): Query {
    val validations = filterValidator.validate(classUtils.getClassName(targetClass), filters)

    ErrorHandlingUtils.throwIfInvalid(validations, FilterValidationErrorFactory())
    val query = Query()

    return query.addCriteria(createDocumentFilter(filters))
  }

  fun createDocumentFilter(filter: IFilter): Criteria = when (filter) {
    is LogicalFilter -> {
      when (filter.type) {
        LogicalFilterType.AND -> Criteria().andOperator(*filter.filters.map { createDocumentFilter(it) }.toTypedArray())
        LogicalFilterType.OR -> Criteria().orOperator(*filter.filters.map { createDocumentFilter(it) }.toTypedArray())
        LogicalFilterType.NOT -> Criteria().not().apply { createDocumentFilter(filter.filters[0]) }
      }
    }
    is Filter -> transformFilter(filter)
    else -> throw UnknownError()
  }

  /**
   * Transform a single custom filter into a MongoDB filter
   */
  fun transformFilter(filter: Filter): Criteria {
    return when (filter.operation) {
      FilterOperation.Equal -> Criteria.where(filter.propertyName).`is`(filter.value)

      FilterOperation.Contains -> Criteria.where(filter.propertyName).regex(".*" + filter.value + ".*")

      FilterOperation.GreaterThan -> Criteria.where(filter.propertyName).gt(filter.value)

      FilterOperation.GreaterThanEq -> Criteria.where(filter.propertyName).gte(filter.value)

      FilterOperation.LowerThan -> Criteria.where(filter.propertyName).lt(filter.value)

      FilterOperation.LowerThanEq -> Criteria.where(filter.propertyName).lte(filter.value)
    }
  }
}