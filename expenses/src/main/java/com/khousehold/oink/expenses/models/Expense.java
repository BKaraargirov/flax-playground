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
@NoArgsConstructor
public class Expense {
  @Id
  private String id;
  private String name;
  private String category;
  private BigDecimal price;

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
