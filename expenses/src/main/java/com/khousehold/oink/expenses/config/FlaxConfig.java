package com.khousehold.oink.expenses.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.khousehold.flax.mongo.filters.QueryFilterFactory;
import com.github.khousehold.oink.commons.filters.DefaultFilterRestrictions;
import com.github.khousehold.oink.commons.filters.FilterFactory;
import com.github.khousehold.oink.commons.filters.FilterValidator;
import com.github.khousehold.oink.commons.filters.models.ClassRestrictions;
import com.github.khousehold.oink.commons.filters.models.FilterRestriction;
import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.github.khousehold.oink.commons.filters.web.IFilterDeserializer;
import com.github.khousehold.oink.commons.reflection.ClassUtils;
import com.github.khousehold.oink.commons.services.DiscoveryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class FlaxConfig {
  private final String searchPath = "com.khousehold.oink.expenses";

  @Bean
  public com.fasterxml.jackson.databind.Module iFilterDeserializer() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(IFilter.class, new IFilterDeserializer());
    return module;
  }

  @Bean
  public FilterFactory<Query> queryFilterFactory(FilterValidator filterValidator, ClassUtils classUtils) {
    return new QueryFilterFactory(filterValidator, classUtils);
  }

  @Bean
  public FilterValidator filterValidator(
      Map<String,ClassRestrictions> classRestrictions,
      List<FilterRestriction> filterRestrictions) {
    return new FilterValidator(classRestrictions, filterRestrictions);
  }

  @Bean
  public List<FilterRestriction> filterRestrictions() {
    return DefaultFilterRestrictions.INSTANCE.getRESTRICTIONS();
  }

  @Bean
  public Map<String, ClassRestrictions> classRestrictions(DiscoveryService discoveryService) {
    return discoveryService.findFilterables(searchPath)
        .stream()
        .map(discoveryService::getFilterableFields)
        .collect(Collectors.toUnmodifiableMap(
            ClassRestrictions::getClassName,
            Function.identity()
        ));
  }

  @Bean
  public DiscoveryService discoveryService(ClassUtils classUtils) {
    return new DiscoveryService(classUtils);
  }

  @Bean
  public ClassUtils classUtils() {
    return ClassUtils.INSTANCE;
  }
}
