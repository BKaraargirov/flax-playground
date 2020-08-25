package com.github.khousehold.flax.mongo.config;

import com.github.khousehold.flax.mongo.filters.QueryFilterFactory;
import com.github.khousehold.oink.commons.filters.FilterFactory;
import com.github.khousehold.oink.commons.filters.FilterValidator;
import com.github.khousehold.oink.commons.reflection.ClassUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.query.Query;

public interface ReactiveFlaxConfig {

  @Bean
  default FilterFactory<Query> queryFilterFactory(FilterValidator filterValidator, ClassUtils classUtils) {
    return new QueryFilterFactory(filterValidator, classUtils);
  }

}
