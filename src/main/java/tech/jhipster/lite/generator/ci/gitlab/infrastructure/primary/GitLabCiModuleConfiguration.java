package tech.jhipster.lite.generator.ci.gitlab.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.ci.gitlab.application.GitLabCiApplicationService;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class GitLabCiModuleConfiguration {

  @Bean
  JHipsterModuleResource gitlabCiModule(GitLabCiApplicationService gitlabCi) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/developer-tools/gitlab-ci")
      .slug("gitlab-ci")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc("Continuous Integration", "Add GitLab CI for Maven Build"))
      .tags("ci", "gitlab")
      .factory(gitlabCi::buildModule);
  }
}
