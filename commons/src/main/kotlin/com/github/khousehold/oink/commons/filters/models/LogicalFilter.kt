package com.github.khousehold.oink.commons.filters.models

class LogicalFilter(
    val type: LogicalFilterType, val filters: List<IFilter>
) : IFilter