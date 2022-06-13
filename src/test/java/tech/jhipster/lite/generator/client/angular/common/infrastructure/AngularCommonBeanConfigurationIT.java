package tech.jhipster.lite.generator.client.angular.common.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonDomainService;

@IntegrationTest
class AngularCommonBeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("angularCommonService")).isNotNull().isInstanceOf(AngularCommonDomainService.class);
  }
}
