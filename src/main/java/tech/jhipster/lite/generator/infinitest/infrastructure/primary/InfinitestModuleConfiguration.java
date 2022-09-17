package tech.jhipster.lite.generator.infinitest.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.infinitest.application.InfinitestApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class InfinitestModuleConfiguration {

  @Bean
  JHipsterModuleResource infinitestModule(InfinitestApplicationService infinitest) {
    return JHipsterModuleResource
      .builder()
      .slug("infinitest-filters")
      .withoutProperties()
      .apiDoc("Base", "Add filter for infinitest")
      .standalone()
      .tags("server", "init", "test")
      .factory(infinitest::build);
  }
}
