package tech.jhipster.lite.module.infrastructure.primary;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.module.domain.ForcedProjectFolder;
import tech.jhipster.lite.module.domain.FreeProjectFolder;
import tech.jhipster.lite.module.domain.JHipsterProjectFolderFactory;

@Configuration
class ProjectFolderConfiguration {

  @Value("${application.forced-project-folder:}")
  private String forcedProjectFolder;

  @Bean
  @ConditionalOnProperty(value = "application.forced-project-folder")
  JHipsterProjectFolderFactory forcedProjectFolder() {
    return new ForcedProjectFolder(forcedProjectFolder);
  }

  @Bean
  @ConditionalOnProperty(value = "application.forced-project-folder", matchIfMissing = true, havingValue = "dummy")
  JHipsterProjectFolderFactory freeProjectFolder() {
    return new FreeProjectFolder();
  }
}
