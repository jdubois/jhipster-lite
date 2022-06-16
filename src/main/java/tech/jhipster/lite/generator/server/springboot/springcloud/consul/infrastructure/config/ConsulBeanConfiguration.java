package tech.jhipster.lite.generator.server.springboot.springcloud.consul.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.springcloud.common.domain.SpringCloudCommonService;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulDomainService;
import tech.jhipster.lite.generator.server.springboot.springcloud.consul.domain.ConsulService;

@Configuration
public class ConsulBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringCloudCommonService springCloudCommonService;
  private final DockerImages dockerImages;

  public ConsulBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringCloudCommonService springCloudCommonService,
    DockerImages dockerImages
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springCloudCommonService = springCloudCommonService;
    this.dockerImages = dockerImages;
  }

  @Bean
  public ConsulService consulService() {
    return new ConsulDomainService(buildToolService, projectRepository, springCloudCommonService, dockerImages);
  }
}
