package com.khousehold.oink.expenses.config;

import com.github.khousehold.flax.mongo.config.ReactiveFlaxConfig;
import com.github.khousehold.flax.spring.config.FlaxConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExpenseConfig implements ReactiveFlaxConfig, FlaxConfig {
  @Override
  public String getSearchPath() {
    return "com.khousehold.oink.expenses";
  }
}
