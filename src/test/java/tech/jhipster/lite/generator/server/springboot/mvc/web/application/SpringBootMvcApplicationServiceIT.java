package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.core.domain.SpringBoot.*;
import static tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcAssertFiles.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.DefaultConfig;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;

@IntegrationTest
class SpringBootMvcApplicationServiceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  MavenApplicationService mavenApplicationService;

  @Autowired
  SpringBootApplicationService springBootApplicationService;

  @Autowired
  SpringBootMvcApplicationService springBootMvcApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.init(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"ERROR\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvc() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"ERROR\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvcWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 7419);
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=7419");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"ERROR\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootMvcWithInvalidServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", "chips");
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootMvc(project);

    assertTomcat(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"org.springframework.web\" level=\"ERROR\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootUndertow() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootUndertow(project);

    assertUndertow(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=8080");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"io.undertow\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddSpringBootUndertowWithServerPort() {
    Project project = tmpProject();
    project.addConfig("serverPort", 1664);
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addSpringBootUndertow(project);

    assertUndertow(project);

    assertFileContent(project, getPath(MAIN_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=1664");
    assertFileContent(project, getPath(TEST_RESOURCES, "config", APPLICATION_PROPERTIES), "server.port=0");
    assertLoggingConfiguration(project, "<logger name=\"io.undertow\" level=\"WARN\" />");

    assertTestUtil(project);
    assertExceptionHandler(project);
    assertCors(project);
  }

  @Test
  void shouldAddExceptionHandler() {
    Project project = tmpProject();
    initApplicationService.init(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    springBootMvcApplicationService.addExceptionHandler(project);

    assertTestUtil(project);
    assertExceptionHandler(project);
  }

  private void assertExceptionHandler(Project project) {
    assertFileContent(project, POM_XML, "<problem-spring.version>");
    assertFileContent(project, POM_XML, "<problem-spring-web.version>");

    assertZalandoProblem(project);
    assertSpringBootStarterValidation(project);
    assertExceptionHandlerProperties(project);
    assertExceptionHandlerFiles(project);
  }

  private void assertCors(Project project) {
    assertCorsFiles(project);
    assertCorsProperties(project);
  }

  private void assertTomcat(Project project) {
    assertFileContent(project, POM_XML, springBootStarterWebDependency());
  }

  private void assertUndertow(Project project) {
    assertFileContent(project, POM_XML, springBootStarterWebWithoutTomcat());
    assertFileContent(project, POM_XML, springBootStarterUndertowDependency());
  }

  private void assertZalandoProblem(Project project) {
    assertFileContent(project, POM_XML, zalandoProblemDependency());
  }

  private void assertSpringBootStarterValidation(Project project) {
    assertFileContent(project, POM_XML, springBootStarterValidationDependency());
  }

  private void assertExceptionHandlerProperties(Project project) {
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "/config/application.properties"),
      List.of(
        "application.exception.details=false",
        "application.exception.package=org.,java.,net.,javax.,com.,io.,de.,com.mycompany.myapp"
      )
    );
    assertFileContent(project, getPath(TEST_RESOURCES, "/config/application.properties"), "application.exception.package=org.,java.");
  }

  private void assertExceptionHandlerFiles(Project project) {
    String packagePath = getPath(
      project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH),
      TECHNICAL_INFRASTRUCTURE_PRIMARY,
      "exception"
    );
    List<String> listClass = List.of(
      "BadRequestAlertException.java",
      "ErrorConstants.java",
      "ExceptionTranslator.java",
      "FieldErrorDTO.java",
      "HeaderUtil.java",
      "ProblemConfiguration.java"
    );
    listClass.forEach(javaClass -> assertFileExist(project, getPath(MAIN_JAVA, packagePath), javaClass));
    assertFileContent(
      project,
      getPath(MAIN_JAVA, packagePath, "ExceptionTranslator.java"),
      "package com.mycompany.myapp.technical.infrastructure.primary.exception;"
    );

    List<String> listTestClass = List.of(
      "BadRequestAlertExceptionTest.java",
      "ExceptionTranslatorIT.java",
      "ExceptionTranslatorTestController.java",
      "FieldErrorDTOTest.java",
      "HeaderUtilTest.java"
    );
    listTestClass.forEach(testClass -> assertFileExist(project, getPath(TEST_JAVA, packagePath), testClass));
  }

  private void assertTestUtil(Project project) {
    assertFileExist(project, getPath(TEST_JAVA, project.getPackageNamePath().orElse(DefaultConfig.PACKAGE_PATH)), "TestUtil.java");
  }

  public void assertLoggingConfiguration(Project project, String loggerEntry) {
    assertFileContent(project, getPath(MAIN_RESOURCES, "logback-spring.xml"), loggerEntry);
    assertFileContent(project, getPath(TEST_RESOURCES, "logback.xml"), loggerEntry);
  }
}
