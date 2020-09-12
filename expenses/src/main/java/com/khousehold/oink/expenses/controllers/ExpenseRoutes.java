package com.khousehold.oink.expenses.controllers;

import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.khousehold.oink.expenses.contracts.UpsertExpenseRequest;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.services.ExpenseService;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class ExpenseRoutes {
  @Bean
  RouterFunction<ServerResponse> expenseCRUDRoutes(ExpenseService expenseService) {
    return route()
        .POST("/expense", request ->
                ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(request.body(BodyExtractors
                        .toMono(UpsertExpenseRequest.class))
                        .flatMap(expenseService::createExpense),
                    Expense.class)
        )
        .PUT("/expense", request ->
            ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(request.body(BodyExtractors
                        .toMono(UpsertExpenseRequest.class))
                        .flatMap(expenseService::createExpense),
                    Expense.class)
        )
        .DELETE("/expense/{id}", request ->
          ok()
              .contentType(MediaType.APPLICATION_JSON)
              .body(
                  expenseService.deleteExpenseById(request.pathVariable("id")),
                  Boolean.class
              )
        )
        .GET("/expenses", request ->
            ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(request.body(BodyExtractors
                .toMono(IFilter.class))
                .flatMapMany(expenseService::getExpenses),
                Expense.class
            ))
        .build();
    }
}
