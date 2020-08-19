package com.github.khousehold.oink.commons.services.testData

import com.github.khousehold.oink.commons.filters.annotations.Filterable
import com.github.khousehold.oink.commons.filters.annotations.NotFilterable

@Filterable
data class NotCompletelyFilterableClass(
    val age: Int,
    val name: String,
    @NotFilterable val address: String,
    private val sex: Char
) {
}