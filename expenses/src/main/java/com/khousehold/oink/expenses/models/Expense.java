package com.khousehold.oink.expenses.models;

import com.github.khousehold.oink.commons.filters.annotations.Filterable;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Filterable
@Data
public class Expense {
  @Id
  private final String id;
  private final String name;
  private final String category;
  private final BigDecimal price;
}
