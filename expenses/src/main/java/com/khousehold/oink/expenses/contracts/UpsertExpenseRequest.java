package com.khousehold.oink.expenses.contracts;

import com.khousehold.oink.expenses.models.Expense;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpsertExpenseRequest {
    private Expense expense;
}
