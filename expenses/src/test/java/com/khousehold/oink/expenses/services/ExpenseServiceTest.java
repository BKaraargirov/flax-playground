package com.khousehold.oink.expenses.services;

import com.khousehold.oink.expenses.contracts.UpsertExpenseRequest;
import com.khousehold.oink.expenses.models.Expense;
import com.khousehold.oink.expenses.repositories.ExpenseRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ExpenseServiceTest {
    private ExpenseRepository expenseRepositoryMock = mock(ExpenseRepository.class);

    private ExpenseService unitUnderTest = new ExpenseService(expenseRepositoryMock);

    @Test
    public void expenseIdIsNullifiedOnInsertTest() {
        var newExpense = new Expense(UUID.randomUUID().toString(), "test-expense",
                "test-category", BigDecimal.valueOf(15));
        var expectedExpense = new Expense(
                null, newExpense.getName(), newExpense.getCategory(), newExpense.getPrice()
        );

        unitUnderTest.createExpense(new UpsertExpenseRequest(newExpense));

        verify(expenseRepositoryMock, times(1)).save(expectedExpense);
    }

    @Test
    public void expenseIdIsNotModifiedOnUpdateTest() {
        var newExpense = new Expense(UUID.randomUUID().toString(), "test-expense",
            "test-category", BigDecimal.valueOf(15));

        unitUnderTest.updateExpense(new UpsertExpenseRequest(newExpense));

        verify(expenseRepositoryMock, times(1)).save(newExpense);
    }

    @Test
    public void deleteIsCalledWithUnmodifiedIdOnceTest() {
        var id = "test-id";
        Mono<Long> successfulResponse = Mono.just(1L) ;
        when(expenseRepositoryMock.deleteCountById(id)).thenReturn(successfulResponse);

        var result = unitUnderTest.deleteExpenseById(id);

        Assertions.assertThat(result.block()).isTrue();
        verify(expenseRepositoryMock, times(1)).deleteCountById(id);
    }

    @Test
    public void nothingWasDeletedTest() {
        var id = "test-id";
        Mono<Long> successfulResponse = Mono.just(0L) ;
        when(expenseRepositoryMock.deleteCountById(id)).thenReturn(successfulResponse);

        var result = unitUnderTest.deleteExpenseById(id);

        Assertions.assertThat(result.block()).isFalse();
        verify(expenseRepositoryMock, times(1)).deleteCountById(id);
    }

}
