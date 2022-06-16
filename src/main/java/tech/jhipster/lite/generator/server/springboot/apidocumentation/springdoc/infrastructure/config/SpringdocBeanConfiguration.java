package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocDomainService;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocService;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@Configuration
public class SpringdocBeanConfiguration {

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;
  private final ProjectJavaDependenciesRepository projectJavaDependenciesRepository;

  public SpringdocBeanConfiguration(
    BuildToolService buildToolService,
    ProjectRepository projectRepository,
    SpringBootCommonService springBootCommonService,
    ProjectJavaDependenciesRepository projectJavaDependenciesRepository
  ) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.projectJavaDependenciesRepository = projectJavaDependenciesRepository;
  }

  @Bean
  public SpringdocService springdocService() {
    return new SpringdocDomainService(buildToolService, projectRepository, springBootCommonService, projectJavaDependenciesRepository);
  }
}
