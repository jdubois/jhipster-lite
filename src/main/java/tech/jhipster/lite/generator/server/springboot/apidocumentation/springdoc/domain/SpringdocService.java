package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringdocService {
  void init(Project project);

  void addSpringdocDependency(Project project);

  void addJavaFiles(Project project);

  void addProperties(Project project);

  void initWithSecurityJWT(Project project);
}
