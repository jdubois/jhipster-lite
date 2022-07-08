package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain.SpringdocDomainService;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;

@Configuration
public class SpringdocBeanConfiguration {

  private final ProjectJavaDependenciesRepository projectJavaDependenciesRepository;

  public SpringdocBeanConfiguration(ProjectJavaDependenciesRepository projectJavaDependenciesRepository) {
    this.projectJavaDependenciesRepository = projectJavaDependenciesRepository;
  }

  @Bean
  public SpringdocDomainService springdocService() {
    return new SpringdocDomainService(projectJavaDependenciesRepository);
  }
}
