package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.cache.ehcache.application.EhcacheAssert.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class EhcacheApplicationServiceIT {

  @Autowired
  private EhcacheApplicationService ehcacheApplicationService;

  @Test
  void shouldInitJavaConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.initJavaConfiguration(project);

    assertInitJavaConfiguration(project);
  }

  @Test
  void shouldInitXmlConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.initXmlConfiguration(project);

    assertInitXmlConfiguration(project);
  }

  @Test
  void shouldAddDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);

    ehcacheApplicationService.addDependencies(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddXmlDependencies() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);

    ehcacheApplicationService.addXmlDependencies(project);

    assertXmlDependencies(project);
  }

  @Test
  void shouldEnableCaching() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.addEnableCaching(project);

    assertEnableCaching(project);
  }

  @Test
  void shouldAddJavaConfig() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.addJavaConfig(project);

    assertJavaFiles(project);
  }

  @Test
  void shouldAddJavaProperties() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.addJavaProperties(project);

    assertProperties(project);
  }

  @Test
  void shouldAddEhcacheXml() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    project.addConfig(PACKAGE_NAME, "tech.jhipster.baz");
    TestJHipsterModules.applyInit(project);

    ehcacheApplicationService.addEhcacheXml(project);

    assertEhcacheXml(project);
  }

  @Test
  void shouldAddXmlProperty() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.addXmlProperty(project);

    assertXmlProperty(project);
  }

  @Test
  void shouldAddLoggerInConfiguration() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "bar");
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);

    ehcacheApplicationService.addLoggerInConfiguration(project);

    assertLoggerInConfig(project);
  }
}
