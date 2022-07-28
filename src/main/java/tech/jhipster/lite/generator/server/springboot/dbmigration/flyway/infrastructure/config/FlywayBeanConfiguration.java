package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.config;

import java.time.Clock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayDomainService;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain.FlywayService;

@Configuration
public class FlywayBeanConfiguration {

  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public FlywayBeanConfiguration(ProjectRepository projectRepository, SpringBootCommonService springBootCommonService, Clock clock) {
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Bean
  public FlywayService flywayService() {
    return new FlywayDomainService(projectRepository, springBootCommonService, clock);
  }
}
