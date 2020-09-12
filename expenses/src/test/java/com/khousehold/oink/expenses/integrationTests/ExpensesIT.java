package com.khousehold.oink.expenses.integrationTests;

import com.khousehold.oink.expenses.contracts.UpsertExpenseRequest;
import com.khousehold.oink.expenses.controllers.ExpenseRoutes;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.repositories.ExpenseRepository;
import com.khousehold.oink.expenses.services.ExpenseService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ExpenseRoutes.class, ExpenseService.class})
@WebFluxTest
public class ExpensesIT {
  @Autowired
  private ApplicationContext context;

  @MockBean
  private ExpenseRepository expenseRepository;

  private WebTestClient webTestClient;

  @Before
  public void setUp() {
    webTestClient = WebTestClient.bindToApplicationContext(context).build();
  }

  @Test
  public void expenseCreationTest() {
    var expense = new Expense(null, "test-expense",
        "test-category", BigDecimal.valueOf(15));

    when(expenseRepository.save(any())).thenAnswer(i -> Mono.just(((Expense)i.getArguments()[0]).generateId()));

    webTestClient.post()
        .uri("/expense")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(new UpsertExpenseRequest(expense)), UpsertExpenseRequest.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Expense.class)
        .value(expenseResponse -> {
              Assertions.assertThat(expenseResponse.getId()).isNotNull();
              Assertions.assertThat(expenseResponse.getName()).isEqualTo("test-expense");
              Assertions.assertThat(expenseResponse.getCategory()).isEqualTo("test-category");
              Assertions.assertThat(expenseResponse.getPrice()).isEqualTo( BigDecimal.valueOf(15));
            }
        );

  }
}
