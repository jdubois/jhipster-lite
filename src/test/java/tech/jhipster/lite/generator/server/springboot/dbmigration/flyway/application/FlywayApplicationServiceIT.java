package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayAssert.*;

import java.time.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.buildtool.maven.application.MavenApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class FlywayApplicationServiceIT {

  @Autowired
  private FlywayApplicationService flywayApplicationService;

  @SpyBean
  private Clock clock;

  @Autowired
  private MavenApplicationService mavenApplicationService;

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldInit() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);

    // When
    flywayApplicationService.init(project);

    // Then
    assertDependencies(project);
    assertInitSqlFile(project);
    assertProperties(project);
  }

  @Test
  void shouldAddUserAuthorityChangelog() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    TestJHipsterModules.applyInit(project);
    mavenApplicationService.addPomXml(project);
    springBootApplicationService.init(project);
    flywayApplicationService.init(project);

    // When
    flywayApplicationService.addUserAuthorityChangelog(project);

    // Then
    assertUserAuthoritySqlFile(project);
  }
}
