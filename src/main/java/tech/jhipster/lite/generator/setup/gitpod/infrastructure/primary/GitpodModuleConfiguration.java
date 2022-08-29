package tech.jhipster.lite.generator.setup.gitpod.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.gitpod.application.GitpodApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitpodModuleConfiguration {

  @Bean
  JHipsterModuleResource gitpodModule(GitpodApplicationService gitPods) {
    return JHipsterModuleResource
      .builder()
      .slug("gitpod")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Gitpod", "Init Gitpod configuration files"))
      .standalone()
      .tags("setup")
      .factory(gitPods::buildModule);
  }
}
