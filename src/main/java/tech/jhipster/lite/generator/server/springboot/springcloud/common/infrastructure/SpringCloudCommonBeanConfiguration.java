package tech.jhipster.lite.generator.server.springboot.springcloud.common.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;

@Configuration
public class SpringCloudCommonBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerImages dockerImages;

  public SpringCloudCommonBeanConfiguration(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    DockerImages dockerImages
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerImages = dockerImages;
  }

  @Bean
  public SpringCloudCommonService springCloudCommonService() {
    return new SpringCloudCommonDomainService(projectRepository, buildToolService, dockerImages);
  }
}
