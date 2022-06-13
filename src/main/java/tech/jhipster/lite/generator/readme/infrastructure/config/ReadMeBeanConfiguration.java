package tech.jhipster.lite.generator.readme.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.readme.domain.ReadMeDomainService;
import tech.jhipster.lite.generator.readme.domain.ReadMeService;

@Configuration
public class ReadMeBeanConfiguration {

  private final ProjectRepository projectRepository;

  public ReadMeBeanConfiguration(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Bean
  public ReadMeService readMeService() {
    return new ReadMeDomainService(projectRepository);
  }
}
