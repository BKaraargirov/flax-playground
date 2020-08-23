package com.khousehold.oink.expenses.models;

import com.github.khousehold.oink.commons.filters.annotations.Filterable;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Filterable
@Data
public class Expense {
  @Id
  private final String id;
  private final String name;
  private final String category;
  private final BigDecimal price;

  /**
   * Return a copy of the expense with a generated id.
   * @return
   */
  public Expense generateId() {
    return new Expense(
            UUID.randomUUID().toString(),
            this.name,
            this.category,
            this.price
    );
  }
}
