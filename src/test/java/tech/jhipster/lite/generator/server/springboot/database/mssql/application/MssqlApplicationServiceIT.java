package tech.jhipster.lite.generator.server.springboot.database.mssql.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mssql.domain.Mssql;

@IntegrationTest
class MssqlApplicationServiceIT {

  @Autowired
  MssqlApplicationService mssqlApplicationService;

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mssqlApplicationService.init(project);

    assertFileContent(project, POM_XML, springBootStarterDataJpa());
    assertFileContent(project, POM_XML, mssqlDriver());
    assertFileContent(project, POM_XML, hibernateCore());
    assertFileContent(project, POM_XML, hikari());

    assertFileExist(project, "src/main/docker/mssqlserver.yml");
    assertFileContent(project, "src/main/docker/mssqlserver.yml", "MSSQL_DATABASE=jhipster");

    String mariadbPath = getPath("com/mycompany/myapp/technical/infrastructure/secondary/mssqlserver");
    assertFileExist(project, getPath(MAIN_JAVA, mariadbPath, "DatabaseConfiguration.java"));

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      "spring.datasource.url=jdbc:sqlserver://localhost:1433;database=jhipster"
    );
    assertTestContainersWasAdded(project);
    assertExtensionForDatabaseContainerWasAdded(project);
    assertLoggerInConfig(project);
  }

  private void assertTestContainersWasAdded(Project project) {
    Optional<String> test = Optional.of("test");
    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(project, POM_XML, testcontainers());
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      "spring.datasource.password=yourStrong(!)Password"
    );
    assertFileContent(project, getPath(TEST_RESOURCES, "config/application.properties"), "spring.datasource.username=SA");
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "config/application.properties"),
      "spring.datasource.url=jdbc:tc:sqlserver:latest://;database=jhipster;trustServerCertificate=true?TC_TMPFS=/testtmpfs:rw"
    );
    assertFileExist(project, "src/test/resources/container-license-acceptance.txt");
  }

  private void assertExtensionForDatabaseContainerWasAdded(Project project) {
    final String projectTestPath = "src/test/java/com/mycompany/myapp";
    assertFileExist(project, projectTestPath + "/MssqlTestContainerExtension.java");
    assertFileContent(project, getPath(projectTestPath, "IntegrationTest.java"), "@ExtendWith(MssqlTestContainerExtension.class)");
    assertFileContent(project, getPath(projectTestPath, "IntegrationTest.java"), "import org.junit.jupiter.api.extension.ExtendWith;");
  }

  private void assertLoggerInConfig(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, LOGGING_CONFIGURATION),
      List.of(
        "<logger name=\"org.hibernate\" level=\"WARN\" />",
        "<logger name=\"org.hibernate.ejb.HibernatePersistence\" level=\"OFF\" />",
        "<logger name=\"org.hibernate.validator\" level=\"WARN\" />"
      )
    );
  }

  private List<String> springBootStarterDataJpa() {
    return List.of(
      "<dependency>",
      "<groupId>org.springframework.boot</groupId>",
      "<artifactId>spring-boot-starter-data-jpa</artifactId>",
      "</dependency>"
    );
  }

  private List<String> mssqlDriver() {
    return List.of("<dependency>", "<groupId>com.microsoft.sqlserver</groupId>", "<artifactId>mssql-jdbc</artifactId>", "</dependency>");
  }

  private List<String> hibernateCore() {
    return List.of("<dependency>", "<groupId>org.hibernate</groupId>", "<artifactId>hibernate-core</artifactId>", "</dependency>");
  }

  private List<String> hikari() {
    return List.of("<dependency>", "<groupId>com.zaxxer</groupId>", "<artifactId>HikariCP</artifactId>", "</dependency>");
  }

  private List<String> testcontainers() {
    return List.of(
      "<dependency>",
      "<groupId>org.testcontainers</groupId>",
      "<artifactId>mssqlserver</artifactId>",
      "<version>${testcontainers.version}</version>",
      "<scope>test</scope>",
      "</dependency>"
    );
  }
}
