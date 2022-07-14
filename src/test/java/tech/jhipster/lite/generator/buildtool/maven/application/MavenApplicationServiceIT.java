package tech.jhipster.lite.generator.buildtool.maven.application;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.maven.domain.Maven;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class MavenApplicationServiceIT {

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Test
  void shouldAddDependency() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    mavenApplicationService.addDependency(project, dependency);

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter</artifactId>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldNotAddDependencyWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());
    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();

    assertThatThrownBy(() -> mavenApplicationService.addDependency(project, dependency)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddDependencyOptional() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-devtools").optional().build();
    mavenApplicationService.addDependency(project, dependency);

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-devtools</artifactId>",
        "<optional>true</optional>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyWithScopeTest() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-test")
      .scope("test")
      .build();
    mavenApplicationService.addDependency(project, dependency);

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-test</artifactId>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyWithExclusions() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter-web").build();
    Dependency dependencyToExclude = Dependency
      .builder()
      .groupId("org.springframework.boot")
      .artifactId("spring-boot-starter-tomcat")
      .build();
    mavenApplicationService.addDependency(project, dependency, List.of(dependencyToExclude));

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-web</artifactId>",
        "<exclusions>",
        "<exclusion>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-tomcat</artifactId>",
        "</exclusion>",
        "</exclusions>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyOnlyOneTime() throws Exception {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency.builder().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
    mavenApplicationService.addDependency(project, dependency);
    mavenApplicationService.addDependency(project, dependency);

    assertFileContentManyTimes(
      project,
      POM_XML,
      Maven.getDependencyHeader(dependency, DEFAULT_INDENTATION).indent(2 * DEFAULT_INDENTATION),
      1
    );
  }

  @Test
  void shouldAddDependencyManagement() {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-starter-bootstrap")
      .version("\\${spring-cloud.version}")
      .scope("import")
      .type("pom")
      .build();
    mavenApplicationService.addDependencyManagement(project, dependency);

    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.cloud</groupId>",
        "<artifactId>spring-cloud-starter-bootstrap</artifactId>",
        "<version>${spring-cloud.version}</version>",
        "<scope>import</scope>",
        "<type>pom</type>",
        "</dependency>"
      )
    );
  }

  @Test
  void shouldAddDependencyManagementOnlyOneTime() throws Exception {
    Project project = tmpProjectWithPomXml();

    Dependency dependency = Dependency
      .builder()
      .groupId("org.springframework.cloud")
      .artifactId("spring-cloud-starter-bootstrap")
      .version("\\${spring-cloud.version}")
      .scope("import")
      .type("pom")
      .build();
    mavenApplicationService.addDependencyManagement(project, dependency);
    mavenApplicationService.addDependencyManagement(project, dependency);

    assertFileContentManyTimes(
      project,
      POM_XML,
      Maven.getDependencyHeader(dependency, DEFAULT_INDENTATION).indent(3 * DEFAULT_INDENTATION),
      1
    );
  }

  @Test
  void shouldAddProperty() {
    Project project = tmpProjectWithPomXml();

    mavenApplicationService.addProperty(project, "testcontainers", "0.0.0");

    assertFileContent(project, POM_XML, "    <testcontainers>0.0.0</testcontainers>");
  }

  @Test
  void shouldNotAddPropertyWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());

    assertThatThrownBy(() -> mavenApplicationService.addProperty(project, "testcontainers", "0.0.0"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddPropertyOnlyOneTime() throws Exception {
    Project project = tmpProjectWithPomXml();

    String key = "testcontainers";
    String version = "0.0.0";
    mavenApplicationService.addProperty(project, key, version);
    mavenApplicationService.addProperty(project, key, version);

    assertFileContentManyTimes(project, POM_XML, Maven.getProperty(key, ".*"), 1);
  }

  @Test
  void shouldDeleteProperty() {
    Project project = tmpProjectWithPomXml();

    mavenApplicationService.addProperty(project, "my-key.version", "1.0");

    assertFileContent(project, POM_XML, "    <my-key.version>1.0</my-key.version>");

    mavenApplicationService.deleteProperty(project, "my-key.version");

    assertFileNoContent(project, POM_XML, "    <my-key.version>1.0</my-key.version>");
  }

  @Test
  void shouldNotDeletePropertyWhenNoPomXml() throws Exception {
    Project project = tmpProject();
    FileUtils.createFolder(project.getFolder());

    assertThatThrownBy(() -> mavenApplicationService.deleteProperty(project, "java")).isExactlyInstanceOf(GeneratorException.class);
  }
}
