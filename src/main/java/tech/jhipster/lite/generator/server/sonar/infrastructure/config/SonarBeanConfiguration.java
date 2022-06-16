package tech.jhipster.lite.generator.server.sonar.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.sonar.domain.SonarDomainService;
import tech.jhipster.lite.generator.server.sonar.domain.SonarService;

@Configuration
public class SonarBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerImages dockerImages;

  public SonarBeanConfiguration(ProjectRepository projectRepository, BuildToolService buildToolService, DockerImages dockerImages) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerImages = dockerImages;
  }

  @Bean
  public SonarService sonarService() {
    return new SonarDomainService(projectRepository, buildToolService, dockerImages);
  }
}
