package tech.jhipster.lite.generator.ci.gitlab.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.gitlab.application.GitLabCiApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class GitLabCiModuleConfiguration {

  @Bean
  JHipsterModuleResource gitlabCiModule(GitLabCiApplicationService gitlabCi) {
    return JHipsterModuleResource
      .builder()
      .slug("gitlab-ci")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Continuous Integration", "Add GitLab CI for Maven Build"))
      .organization(JHipsterModuleOrganization.builder().addModuleDependency("maven-java").build())
      .tags("ci", "gitlab")
      .factory(gitlabCi::buildModule);
  }
}
