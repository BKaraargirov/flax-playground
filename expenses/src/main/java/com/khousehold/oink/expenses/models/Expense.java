package com.khousehold.oink.expenses.models;

import com.github.khousehold.oink.commons.filters.annotations.Filterable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.UUID;

@Filterable
@Data
@AllArgsConstructor
public class Expense {
  @Id
  private final String id;
  private final String name;
  private final String category;
  private final BigDecimal price;

  public Expense() {
    this.id = null;
    this.name = null;
    this.category = null;
    this.price = null;
  }

  /**
   * Return a copy of the expense with a generated id.
   */
  public Expense generateId() {
    return new Expense(
            UUID.randomUUID().toString(),
            this.name,
            this.category,
            this.price
    );
  }

  /**
   * Return a copy of the expense with a null id. This is to avoid updating existing expenses on insert
   */
  public Expense nullifyId() {
    return new Expense(
        null,
        this.name,
        this.category,
        this.price
    );
  }
}
