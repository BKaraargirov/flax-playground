package com.khousehold.oink.expenses.repositories;

import com.khousehold.oink.expenses.models.Expense;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface ExpenseRepository extends ReactiveMongoRepository<Expense, String>, ExpenseQueryRepository {
  Mono<Long> deleteCountById(String id);
}
