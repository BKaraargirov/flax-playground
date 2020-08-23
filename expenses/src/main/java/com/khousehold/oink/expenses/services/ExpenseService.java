package com.khousehold.oink.expenses.services;

import com.github.khousehold.flax.mongo.filters.BsonFilterFactory;
import com.github.khousehold.oink.commons.filters.FilterFactory;
import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ExpenseService {
  private final ExpenseRepository expenseRepository;

  public Flux<Expense> getExpenses(IFilter filter) {
    return this.expenseRepository.findExpenses(filter);
  }
}
