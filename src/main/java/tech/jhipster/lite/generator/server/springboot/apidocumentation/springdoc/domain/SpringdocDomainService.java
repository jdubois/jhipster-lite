package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.ProjectJavaDependenciesRepository;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringdocDomainService {

  protected static final DependencyId WEBFLUX_DEPENDENCY_ID = new DependencyId(
    new GroupId("org.springframework.boot"),
    new ArtifactId("spring-boot-starter-webflux")
  );

  private final ProjectJavaDependenciesRepository projectJavaDependenciesRepository;
  private final SpringdocModuleFactory springdocModuleFactory;

  public SpringdocDomainService(ProjectJavaDependenciesRepository projectJavaDependenciesRepository) {
    this.projectJavaDependenciesRepository = projectJavaDependenciesRepository;
    this.springdocModuleFactory = new SpringdocModuleFactory();
  }

  public JHipsterModule buildSpringdocModule(JHipsterModuleProperties properties) {
    return projectJavaDependenciesRepository
      .get(properties.projectFolder())
      .dependencies()
      .get(WEBFLUX_DEPENDENCY_ID)
      .map(d -> springdocModuleFactory.buildModuleForWebflux(properties))
      .orElse(springdocModuleFactory.buildModuleForMvc(properties));
  }

  public JHipsterModule buildSpringdocModuleWithSecurityJWT(JHipsterModuleProperties properties) {
    return projectJavaDependenciesRepository
      .get(properties.projectFolder())
      .dependencies()
      .get(WEBFLUX_DEPENDENCY_ID)
      .map(d -> springdocModuleFactory.buildModuleWithSecurityJwtForWebflux(properties))
      .orElse(springdocModuleFactory.buildModuleWithSecurityJwtForMvc(properties));
  }
}
