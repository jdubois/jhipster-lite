package tech.jhipster.lite.generator.client.angular.admin.health.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealthDomainService;

@IntegrationTest
class AngularHealthBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("angularHealthService")).isNotNull().isInstanceOf(AngularHealthDomainService.class);
  }
}
