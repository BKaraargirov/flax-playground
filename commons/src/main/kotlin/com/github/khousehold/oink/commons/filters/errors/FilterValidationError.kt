package com.github.khousehold.oink.commons.filters.errors

import com.github.khousehold.oink.commons.errors.ValidationError
import com.github.khousehold.oink.commons.errors.ValidationErrorFactory

class FilterValidationErrorFactory: ValidationErrorFactory<FilterError> {
  override fun create(errors: List<FilterError>): ValidationError = FilterValidationError(errors)
}

class FilterValidationError(
  errors: List<FilterError>
) : ValidationError(
    id ="FTR_99_VLD",
    messages = errors,
    origin = "Filtration"
)