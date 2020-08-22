package com.github.khousehold.flax.mongo.filters

import com.github.khousehold.oink.commons.filters.DefaultFilterRestrictions
import com.github.khousehold.oink.commons.filters.FilterValidator
import com.github.khousehold.oink.commons.filters.models.Filter
import com.github.khousehold.oink.commons.filters.models.FilterOperation
import com.github.khousehold.oink.commons.filters.models.LogicalFilter
import com.github.khousehold.oink.commons.filters.models.LogicalFilterType
import com.github.khousehold.oink.commons.reflection.ClassUtils
import com.github.khousehold.oink.commons.services.DiscoveryService
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class QueryFilterFactoryTests: StringSpec({
  class TestClass(
      propOne: Int,
      propTwo: Int
  )

  val discoveryService = DiscoveryService()
  val clsRestriction = discoveryService.getFilterableFields(TestClass::class)
  val mapping = mapOf(ClassUtils.getClassName(TestClass::class) to clsRestriction)
  val filterValidator = FilterValidator(mapping, DefaultFilterRestrictions.RESTRICTIONS)
  val filterFactory = QueryFilterFactory(filterValidator, ClassUtils)

  "creating filter with AND and 2 integers" {
    val filters = LogicalFilter(
        type = LogicalFilterType.AND,
        filters = listOf(
            Filter(
                "propOne",
                1,
                FilterOperation.GreaterThan
            ),
            Filter(
                "propTwo",
                -10,
                FilterOperation.LowerThanEq
            )
        )
    )

    val expectedResult = Query(
        Criteria().andOperator(
            Criteria.where("propOne").gt(1),
            Criteria.where("propTwo").lte(-10)
        )
    )

    val actualResult = filterFactory.transformFilters(filters, TestClass::class)

    actualResult shouldBe expectedResult
  }
})