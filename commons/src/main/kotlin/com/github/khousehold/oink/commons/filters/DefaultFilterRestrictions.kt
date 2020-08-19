package com.github.khousehold.oink.commons.filters

import com.github.khousehold.oink.commons.reflection.TypeUtils.isNumber
import com.github.khousehold.oink.commons.reflection.TypeUtils.isTypeOf
import com.github.khousehold.oink.commons.filters.models.FilterOperation.*
import com.github.khousehold.oink.commons.filters.models.FilterRestriction
import com.github.khousehold.oink.commons.reflection.TypeUtils
import java.time.LocalDateTime

object DefaultFilterRestrictions {
  val RESTRICTIONS = listOf(
      FilterRestriction({ k -> isTypeOf(k, String::class) }, listOf(Equal, Contains)),
      FilterRestriction({ k -> isTypeOf(k, TypeUtils.getNullableType(String::class)) }, listOf(Equal)),
      FilterRestriction({ k -> isNumber(k) }, listOf(Equal, GreaterThan, GreaterThanEq, LowerThan, LowerThanEq)),
      FilterRestriction({ k -> isTypeOf(k, LocalDateTime::class) }, listOf(GreaterThan, GreaterThanEq, LowerThan, LowerThanEq))
  // TODO: add year eq, year + month, year + month + day eq,
  )
}