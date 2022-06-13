package tech.jhipster.lite.generator.server.javatool.arch.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;

public class JavaArchUnitAssertFiles {

  private JavaArchUnitAssertFiles() {}

  public static void assertArchunitProperties(Project project) {
    assertFileContent(project, getPath(TEST_RESOURCES, "archunit.properties"), "archRule.failOnEmptyShould=false");
  }

  public static void assertFilesHexaArchTest(Project project) {
    String basePath = project.getPackageNamePath().orElse(getPath(DefaultConfig.PACKAGE_PATH));
    String archPath = getPath(TEST_JAVA, basePath);

    assertFileExist(project, getPath(archPath, "HexagonalArchTest.java"));
  }

  public static void assertArchUnitMavenPlugin(Project project) {
    assertFileContent(project, POM_XML, "<archunit-junit5.version>");
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>com.tngtech.archunit</groupId>",
        "<artifactId>archunit-junit5-api</artifactId>",
        "<version>${archunit-junit5.version}</version>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  public static void assertLoggerInConfiguration(Project project) {
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, LOGGING_TEST_CONFIGURATION),
      List.of("<logger name=\"com.tngtech.archunit\" level=\"WARN\" />")
    );
  }
}
