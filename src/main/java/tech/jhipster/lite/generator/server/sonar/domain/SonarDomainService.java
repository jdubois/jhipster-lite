package tech.jhipster.lite.generator.server.sonar.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Plugin;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SonarDomainService implements SonarService {

  public static final String SOURCE = "server/sonar";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final DockerImages dockerImages;

  public SonarDomainService(ProjectRepository projectRepository, BuildToolService buildToolService, DockerImages dockerImages) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.dockerImages = dockerImages;
  }

  @Override
  public void addSonarJavaBackend(Project project) {
    addPropertiesPlugin(project);
    addSonarScannerPluginManagement(project);
    addPropertiesFile(project);
    addDockerCompose(project);
  }

  @Override
  public void addSonarJavaBackendAndFrontend(Project project) {
    addPropertiesPlugin(project);
    addSonarScannerPluginManagement(project);
    addFullstackPropertiesFile(project);
    addDockerCompose(project);
  }

  private void addPropertiesPlugin(Project project) {
    Plugin plugin = Plugin
      .builder()
      .groupId("org.codehaus.mojo")
      .artifactId("properties-maven-plugin")
      .version("\\${properties-maven-plugin.version}")
      .additionalElements(
        """
            <executions>
              <execution>
                <phase>initialize</phase>
                <goals>
                  <goal>read-project-properties</goal>
                </goals>
                <configuration>
                  <files>
                    <file>sonar-project.properties</file>
                  </files>
                </configuration>
              </execution>
            </executions>"""
      )
      .build();
    buildToolService
      .getVersion(project, "properties-maven-plugin")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "properties-maven-plugin.version", version),
        () -> {
          throw new GeneratorException("Version not found: properties-maven-plugin");
        }
      );
    buildToolService.addPlugin(project, plugin);
  }

  private void addSonarScannerPluginManagement(Project project) {
    Plugin plugin = Plugin
      .builder()
      .groupId("org.sonarsource.scanner.maven")
      .artifactId("sonar-maven-plugin")
      .version("\\${sonar-maven-plugin.version}")
      .build();
    buildToolService
      .getVersion(project, "sonar-maven-plugin")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "sonar-maven-plugin.version", version),
        () -> {
          throw new GeneratorException("Version not found: sonar-maven-plugin");
        }
      );
    buildToolService.addPluginManagement(project, plugin);
  }

  private void addPropertiesFile(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    projectRepository.template(ProjectFile.forProject(project).withSource(SOURCE, "sonar-project.properties").withSameDestination());
  }

  private void addFullstackPropertiesFile(Project project) {
    project.addDefaultConfig(BASE_NAME);
    project.addDefaultConfig(PROJECT_NAME);
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "sonar-fullstack-project.properties")
        .withDestination("", "sonar-project.properties")
    );
  }

  private void addDockerCompose(Project project) {
    project.addDefaultConfig(BASE_NAME);
    String dockerImage = dockerImages.get(Sonar.getSonarqubeDockerImageName()).fullName();
    project.addConfig("sonarqubeDockerImage", dockerImage);

    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "sonar.yml").withDestination("src/main/docker", "sonar.yml")
    );
  }
}
