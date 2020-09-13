package com.github.khousehold.flax.mongo.repositories

import com.github.khousehold.oink.commons.filters.models.IFilter
import reactor.core.publisher.Flux

interface QueryRepository<T> {
  fun findExpenses(filter: IFilter): Flux<T>
}