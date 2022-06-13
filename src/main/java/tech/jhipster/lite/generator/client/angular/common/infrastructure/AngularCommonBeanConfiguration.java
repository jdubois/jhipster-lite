package tech.jhipster.lite.generator.client.angular.common.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonDomainService;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;

@Configuration
public class AngularCommonBeanConfiguration {

  @Bean
  public AngularCommonService angularCommonService() {
    return new AngularCommonDomainService();
  }
}
