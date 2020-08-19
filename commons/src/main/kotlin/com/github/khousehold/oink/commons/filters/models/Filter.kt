package com.github.khousehold.oink.commons.filters.models

data class Filter(
  val propertyName: String,
  val value: Any?,
  val operation: FilterOperation
): IFilter