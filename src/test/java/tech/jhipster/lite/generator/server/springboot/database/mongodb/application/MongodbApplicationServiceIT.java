package tech.jhipster.lite.generator.server.springboot.database.mongodb.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.server.springboot.database.mongodb.MongodbAssert.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class MongodbApplicationServiceIT {

  @Autowired
  private MongodbApplicationService mongodbApplicationService;

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Test
  @DisplayName("Should init project with default values")
  void shouldInit() {
    Project project = tmpProject();
    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    mongodbApplicationService.init(project);

    assertDependencies(project);
    assertDockerMongodb(project);
    assertJavaFiles(project);
    assertTestFiles(project);
    assertConfigFiles(project);
    assertLoggerInConfig(project);
  }
}
