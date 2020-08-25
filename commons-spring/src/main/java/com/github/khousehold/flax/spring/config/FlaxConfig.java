package com.github.khousehold.flax.spring.config;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.github.khousehold.flax.spring.web.FilterDeserializer;
import com.github.khousehold.oink.commons.filters.DefaultFilterRestrictions;
import com.github.khousehold.oink.commons.filters.FilterValidator;
import com.github.khousehold.oink.commons.filters.models.ClassRestrictions;
import com.github.khousehold.oink.commons.filters.models.FilterRestriction;
import com.github.khousehold.oink.commons.filters.models.IFilter;
import com.github.khousehold.oink.commons.reflection.ClassUtils;
import com.github.khousehold.oink.commons.services.DiscoveryService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public interface FlaxConfig {

  String getSearchPath();

  @Bean
  default com.fasterxml.jackson.databind.Module iFilterDeserializer() {
    SimpleModule module = new SimpleModule();
    module.addDeserializer(IFilter.class, new FilterDeserializer());
    return module;
  }

  @Bean
  default FilterValidator filterValidator(
      Map<String,ClassRestrictions> classRestrictions,
      List<FilterRestriction> filterRestrictions) {
    return new FilterValidator(classRestrictions, filterRestrictions);
  }

  @Bean
  default List<FilterRestriction> filterRestrictions() {
    return DefaultFilterRestrictions.INSTANCE.getRESTRICTIONS();
  }

  @Bean
  default Map<String, ClassRestrictions> classRestrictions(DiscoveryService discoveryService) {
    return discoveryService.findFilterables(getSearchPath())
        .stream()
        .map(discoveryService::getFilterableFields)
        .collect(Collectors.toUnmodifiableMap(
            ClassRestrictions::getClassName,
            Function.identity()
        ));
  }

  @Bean
  default DiscoveryService discoveryService(ClassUtils classUtils) {
    return new DiscoveryService(classUtils);
  }

  @Bean
  default ClassUtils classUtils() {
    return ClassUtils.INSTANCE;
  }
}
