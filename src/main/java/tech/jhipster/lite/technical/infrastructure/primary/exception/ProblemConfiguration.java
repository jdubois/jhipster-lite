package tech.jhipster.lite.technical.infrastructure.primary.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
class ProblemConfiguration {

  /*
   * Module for serialization/deserialization of RFC7807 Problem.
   */
  @Bean
  public ProblemModule problemModule() {
    return new ProblemModule();
  }

  @Bean
  public ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }
}
