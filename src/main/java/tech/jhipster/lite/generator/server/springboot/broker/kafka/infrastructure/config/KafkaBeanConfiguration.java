package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.readme.domain.ReadMeService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaDomainService;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.domain.KafkaService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class KafkaBeanConfiguration {

  private final BuildToolService buildToolService;

  private final ProjectRepository projectRepository;

  private final SpringBootCommonService springBootCommonService;

  private final DockerImages dockerImages;

  private final ReadMeService readMeService;

  public KafkaBeanConfiguration(
    final BuildToolService buildToolService,
    final ProjectRepository projectRepository,
    final SpringBootCommonService springBootCommonService,
    final DockerImages dockerImages,
    final ReadMeService readMeService
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.dockerImages = dockerImages;
    this.readMeService = readMeService;
  }

  @Bean
  public KafkaService kafkaService() {
    return new KafkaDomainService(buildToolService, projectRepository, springBootCommonService, dockerImages, readMeService);
  }
}
