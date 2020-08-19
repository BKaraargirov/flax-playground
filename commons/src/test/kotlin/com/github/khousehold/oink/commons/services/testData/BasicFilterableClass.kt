package com.github.khousehold.oink.commons.services.testData

import com.github.khousehold.oink.commons.filters.annotations.Filterable

@Filterable
data class BasicFilterableClass(
        val myStringField: String,
        val myIntFiled: Int
) {
}