package tech.jhipster.lite.module.infrastructure.secondary;

import java.util.Collection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;

@Configuration
class JHipsterModulesResourcesConfiguration {

  @Bean
  JHipsterModulesResources jhipsterModulesResources(Collection<JHipsterModuleResource> modulesResources) {
    return new JHipsterModulesResources(modulesResources);
  }
}
