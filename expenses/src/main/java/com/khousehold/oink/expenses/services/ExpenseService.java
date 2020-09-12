package com.khousehold.oink.expenses.services;

import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.khousehold.oink.expenses.contracts.UpsertExpenseRequest;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.repositories.ExpenseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ExpenseService {
  private final ExpenseRepository expenseRepository;

  public Mono<Expense> createExpense(UpsertExpenseRequest upsertExpenseRequest) {
    var expenseWithNullId = upsertExpenseRequest.getExpense().nullifyId();

    return this.expenseRepository.save(expenseWithNullId);
  }

  public Mono<Expense> updateExpense(UpsertExpenseRequest upsertExpenseRequest) {
    assert(upsertExpenseRequest.getExpense().getId() != null);

    return this.expenseRepository.save(upsertExpenseRequest.getExpense());
  }

  public Flux<Expense> getExpenses(IFilter filter) {
    return this.expenseRepository.findExpenses(filter);
  }

  public Mono<Boolean> deleteExpenseById(String expenseId) {
    return this.expenseRepository.deleteById(expenseId)
        .map(unused -> true);
  }
}
