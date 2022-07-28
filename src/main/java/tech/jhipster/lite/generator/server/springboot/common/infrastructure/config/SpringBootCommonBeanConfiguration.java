package tech.jhipster.lite.generator.server.springboot.common.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class SpringBootCommonBeanConfiguration {

  @Bean
  public SpringBootCommonService springBootCommonService() {
    return new SpringBootCommonDomainService();
  }
}
