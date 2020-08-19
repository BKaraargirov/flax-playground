package com.khousehold.oink.expenses.models;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Expense {
  @Id
  private final String id;
  private final String name;
  private final String category;
  private final BigDecimal price;
}
