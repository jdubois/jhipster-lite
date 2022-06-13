package tech.jhipster.lite.generator.client.angular.core.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.List;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class AngularDomainService implements AngularService {

  public static final String SOURCE = "client/angular/core";
  public static final String SOURCE_WEBAPP = "client/angular/core/src/main/webapp";
  private static final String APP = "src/main/webapp/app";
  public static final String SOURCE_PRIMARY = getPath(SOURCE, APP);
  public static final String DESTINATION_PRIMARY = APP;

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public AngularDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void addAngular(Project project) {
    addCommonAngular(project);
    addAppFiles(project);
  }

  private void addCommonAngular(Project project) {
    addDependencies(project);
    addDevDependencies(project);
    addScripts(project);
    addJestSonar(project);
    addFiles(project);
    addAngularFiles(project);
  }

  public void addAppFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY, APP_COMPONENT_HTML).withDestinationFolder(DESTINATION_PRIMARY)
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE_PRIMARY, APP_COMPONENT).withDestinationFolder(DESTINATION_PRIMARY)
    );

    addImages(project);
  }

  public void addDependencies(Project project) {
    Angular.dependencies().forEach(dependency -> addDependency(project, dependency));
  }

  public void addDevDependencies(Project project) {
    Angular.devDependencies().forEach(devDependency -> addDevDependency(project, devDependency));
  }

  private void addDependency(Project project, String dependency) {
    npmService
      .getVersion("angular", dependency)
      .ifPresentOrElse(
        version -> npmService.addDependency(project, dependency, version),
        () -> {
          throw new GeneratorException("Dependency not found: " + dependency);
        }
      );
  }

  private void addDevDependency(Project project, String devDependency) {
    npmService
      .getVersion("angular", devDependency)
      .ifPresentOrElse(
        version -> npmService.addDevDependency(project, devDependency, version),
        () -> {
          throw new GeneratorException("DevDependency not found: " + devDependency);
        }
      );
  }

  public void addScripts(Project project) {
    Angular.scripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addFiles(Project project) {
    projectRepository.add(ProjectFile.forProject(project).all(SOURCE, Angular.files()));
  }

  public void addAngularFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    List<ProjectFile> files = Angular
      .angularFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE_WEBAPP, entry.getValue()), entry.getKey())
          .withDestinationFolder(getPath(MAIN_WEBAPP, entry.getValue()))
      )
      .toList();

    projectRepository.template(files);
  }

  public void addImages(Project project) {
    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE_WEBAPP, "content/images"), "JHipster-Lite-neon-red.png")
        .withDestinationFolder("src/main/webapp/content/images")
    );

    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE_WEBAPP, "content/images"), "AngularLogo.svg")
        .withDestinationFolder("src/main/webapp/content/images")
    );
  }

  public void addJestSonar(Project project) {
    String oldText = "\"cacheDirectories\": \\[";
    String newText =
      """
        "jestSonar": \\{
            "reportPath": "target/test-results/jest",
            "reportFile": "TESTS-results-sonar.xml"
          \\},
          "cacheDirectories": \\[""";
    projectRepository.replaceText(project, "", PACKAGE_JSON, oldText, newText);
  }
}
