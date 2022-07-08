package tech.jhipster.lite.generator.client.react.security.jwt.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwtDomainService;

@IntegrationTest
class ReactJwtBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("reactJwtService")).isNotNull().isInstanceOf(ReactJwtDomainService.class);
  }
}
