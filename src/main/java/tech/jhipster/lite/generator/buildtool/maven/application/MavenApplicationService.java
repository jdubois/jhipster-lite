package tech.jhipster.lite.generator.buildtool.maven.application;

import java.util.List;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenModuleFactory;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MavenApplicationService {

  private final MavenService mavenService;
  private final MavenModuleFactory factory;

  public MavenApplicationService(MavenService mavenService) {
    this.mavenService = mavenService;

    factory = new MavenModuleFactory();
  }

  public JHipsterModule buildInitModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }

  public void addDependency(Project project, Dependency dependency) {
    mavenService.addDependency(project, dependency);
  }

  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    mavenService.addDependency(project, dependency, exclusions);
  }

  public void addDependencyManagement(Project project, Dependency dependency) {
    mavenService.addDependencyManagement(project, dependency);
  }

  public void addProperty(Project project, String key, String version) {
    mavenService.addProperty(project, key, version);
  }

  public void deleteProperty(Project project, String key) {
    mavenService.deleteProperty(project, key);
  }
}
