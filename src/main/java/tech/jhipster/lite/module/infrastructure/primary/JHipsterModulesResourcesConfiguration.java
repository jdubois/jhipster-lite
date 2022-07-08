package tech.jhipster.lite.module.infrastructure.primary;

import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class JHipsterModulesResourcesConfiguration {

  @Bean
  JHipsterModulesResources jhipsterModulesResources(Collection<JHipsterModuleResource> modulesResources) {
    return new JHipsterModulesResources(modulesResources);
  }
}
