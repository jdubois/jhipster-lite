package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorDomainService;
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain.SpringBootActuatorService;

@Configuration
public class SpringBootActuatorBeanConfiguration {

  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;

  public SpringBootActuatorBeanConfiguration(BuildToolService buildToolService, SpringBootCommonService springBootCommonService) {
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
  }

  @Bean
  public SpringBootActuatorService springBootActuatorService() {
    return new SpringBootActuatorDomainService(buildToolService, springBootCommonService);
  }
}
