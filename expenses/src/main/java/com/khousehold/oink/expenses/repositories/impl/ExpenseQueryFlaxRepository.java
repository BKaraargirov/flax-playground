package com.khousehold.oink.expenses.repositories.impl;

import com.github.khousehold.oink.commons.filters.FilterFactory;
import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.repositories.ExpenseQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class ExpenseQueryFlaxRepository implements ExpenseQueryRepository {
  private final FilterFactory<Query> filterFactory;
  private final ReactiveMongoTemplate template;

  @Override
  public Flux<Expense> findExpenses(IFilter filter) {
    var documentFilter = filterFactory.transformFilters(filter, Expense.class);
    return template.find(documentFilter, Expense.class);
  }
}

