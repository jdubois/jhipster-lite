package tech.jhipster.lite.generator.server.springboot.database.mongodb;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;

import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;

public final class MongodbAssert {

  private MongodbAssert() {}

  public static void assertDependencies(Project project) {
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.springframework.boot</groupId>",
        "<artifactId>spring-boot-starter-data-mongodb</artifactId>",
        "</dependency>"
      )
    );

    assertFileContent(
      project,
      POM_XML,
      List.of("<dependency>", "<groupId>org.mongodb</groupId>", "<artifactId>mongodb-driver-sync</artifactId>", "</dependency>")
    );

    assertFileContent(project, POM_XML, "<testcontainers.version>");
    assertFileContent(project, POM_XML, "</testcontainers.version>");
    assertFileContent(
      project,
      POM_XML,
      List.of(
        "<dependency>",
        "<groupId>org.testcontainers</groupId>",
        "<artifactId>mongodb</artifactId>",
        "<version>${testcontainers.version}</version>",
        "<scope>test</scope>",
        "</dependency>"
      )
    );
  }

  public static void assertDockerMongodb(Project project) {
    assertFileExist(project, "src/main/docker/mongodb.yml");
    assertFileContent(project, "src/main/docker/mongodb.yml", "mongo:");
    assertFileContent(project, "src/main/docker/mongodb.yml", "- 127.0.0.1:27017:27017");
  }

  public static void assertJavaFiles(Project project) {
    String packageNamePath = project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH);
    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);

    String mongodbPath = getPath(packageNamePath, "/technical/infrastructure/secondary/mongodb");
    assertFileExist(project, getPath(MAIN_JAVA, mongodbPath, "MongodbDatabaseConfiguration.java"));
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, mongodbPath, "MongodbDatabaseConfiguration.java"),
      "package " + packageName + ".technical.infrastructure.secondary.mongodb;"
    );

    assertFileExist(project, getPath(MAIN_JAVA, mongodbPath, "JSR310DateConverters.java"));
    assertFileContent(
      project,
      FileUtils.getPath(MAIN_JAVA, mongodbPath, "JSR310DateConverters.java"),
      "package " + packageName + ".technical.infrastructure.secondary.mongodb;"
    );
  }

  public static void assertTestFiles(Project project) {
    String packagePath = project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH);
    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);

    String mongodbPath = getPath(packagePath, "/technical/infrastructure/secondary/mongodb");
    assertFileExist(project, getPath(TEST_JAVA, packagePath, "/technical/infrastructure/secondary/mongodb/JSR310DateConvertersTest.java"));
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, mongodbPath, "JSR310DateConvertersTest.java"),
      "package " + packageName + ".technical.infrastructure.secondary.mongodb;"
    );

    assertFileExist(project, getPath(TEST_JAVA, packagePath, "MongodbTestContainerExtension.java"));
    assertFileContent(
      project,
      getPath(TEST_JAVA, packagePath, "IntegrationTest.java"),
      "import org.junit.jupiter.api.extension.ExtendWith;"
    );
    assertFileContent(project, getPath(TEST_JAVA, packagePath, "IntegrationTest.java"), "@ExtendWith(MongodbTestContainerExtension.class)");

    assertFileExist(project, getPath(TEST_JAVA, packagePath, "TestContainersSpringContextCustomizerFactory.java"));
    assertFileContent(
      project,
      FileUtils.getPath(TEST_JAVA, packagePath, "TestContainersSpringContextCustomizerFactory.java"),
      "package " + packageName + ";"
    );
  }

  public static void assertConfigFiles(Project project) {
    String baseName = project.getBaseName().orElse("jhipster");
    String packageName = project.getPackageName().orElse(DefaultConfig.DEFAULT_PACKAGE_NAME);

    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES),
      List.of("spring.data.mongodb.database=" + baseName, "spring.data.mongodb.uri=mongodb://localhost:27017")
    );

    assertFileExist(project, getPath(TEST_RESOURCES, "META-INF", "spring.factories"));
    assertFileContent(
      project,
      getPath(TEST_RESOURCES, "META-INF", "spring.factories"),
      "org.springframework.test.context.ContextCustomizerFactory = " + packageName + ".TestContainersSpringContextCustomizerFactory"
    );
  }

  public static void assertLoggerInConfig(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, LOGGING_CONFIGURATION),
      List.of("<logger name=\"org.reflections\" level=\"WARN\" />", "<logger name=\"org.mongodb.driver\" level=\"WARN\" />")
    );

    assertFileContent(
      project,
      getPath(TEST_RESOURCES, LOGGING_TEST_CONFIGURATION),
      List.of(
        "<logger name=\"org.reflections\" level=\"WARN\" />",
        "<logger name=\"org.mongodb.driver\" level=\"WARN\" />",
        "<logger name=\"com.github.dockerjava\" level=\"WARN\" />",
        "<logger name=\"org.testcontainers\" level=\"WARN\" />"
      )
    );
  }
}
