package tech.jhipster.lite.generator.client.angular.security.oauth2.infrastructure.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2DomainService;

@IntegrationTest
class AngularOauth2BeanConfigurationIT {

  @Autowired
  ApplicationContext applicationContext;

  @Test
  void shouldGetBean() {
    assertThat(applicationContext.getBean("angularOauth2Service")).isNotNull().isInstanceOf(AngularOauth2DomainService.class);
  }
}
