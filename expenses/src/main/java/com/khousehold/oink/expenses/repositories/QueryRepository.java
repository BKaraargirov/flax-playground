package com.khousehold.oink.expenses.repositories;

import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.khousehold.oink.expenses.models.Expense;
import reactor.core.publisher.Flux;

public interface QueryRepository<T> {
  Flux<T> findExpenses(IFilter filter);
}
