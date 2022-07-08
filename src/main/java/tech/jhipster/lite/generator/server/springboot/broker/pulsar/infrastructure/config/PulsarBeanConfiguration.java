package tech.jhipster.lite.generator.server.springboot.broker.pulsar.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.PulsarDomainService;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.PulsarService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class PulsarBeanConfiguration {

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  private final DockerImages dockerImages;

  public PulsarBeanConfiguration(
    final BuildToolService buildToolService,
    final ProjectRepository projectRepository,
    final SpringBootCommonService springBootCommonService,
    final DockerImages dockerImages
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.dockerImages = dockerImages;
  }

  @Bean
  public PulsarService pulsarService() {
    return new PulsarDomainService(buildToolService, projectRepository, springBootCommonService, dockerImages);
  }
}
