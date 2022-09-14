package tech.jhipster.lite.generator.server.sonar.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SonarModulesFactory {

  private static final JHipsterSource SOURCE = from("server/sonar");
  private static final JHipsterDestination SONAR_PROPERTIES_DESTINATION = to("sonar-project.properties");

  private final DockerImages dockerImages;

  public SonarModulesFactory(DockerImages dockerImages) {
    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildBackendModule(JHipsterModuleProperties properties) {
    return commonModuleFiles(properties)
      .files()
      .add(SOURCE.template("sonar-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  public JHipsterModule buildBackendFrontendModule(JHipsterModuleProperties properties) {
    return commonModuleFiles(properties)
      .files()
      .add(SOURCE.template("sonar-fullstack-project.properties"), SONAR_PROPERTIES_DESTINATION)
      .and()
      .build();
  }

  private JHipsterModuleBuilder commonModuleFiles(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .context()
      .put("sonarqubeDockerImage", dockerImages.get("sonarqube").fullName())
      .and()
      .documentation(documentationTitle("sonar"), SOURCE.template("sonar.md"))
      .startupCommand("""
        docker compose -f src/main/docker/sonar.yml up -d
        ./mvnw clean verify sonar:sonar
        """)
      .javaBuildPlugins()
      .plugin(propertiesPlugin())
      .pluginManagement(sonarPlugin())
      .and()
      .files()
      .add(SOURCE.template("sonar.yml"), toSrcMainDocker().append("sonar.yml"))
      .and();
  }

  private JavaBuildPlugin propertiesPlugin() {
    return JavaBuildPlugin
      .builder()
      .groupId("org.codehaus.mojo")
      .artifactId("properties-maven-plugin")
      .versionSlug("properties-maven-plugin")
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
            </executions>
            """
      )
      .build();
  }

  private JavaBuildPlugin sonarPlugin() {
    return JavaBuildPlugin
      .builder()
      .groupId("org.sonarsource.scanner.maven")
      .artifactId("sonar-maven-plugin")
      .versionSlug("sonar-maven-plugin")
      .build();
  }
}
