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
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class FlywayApplicationServiceIT {

  @Autowired
  private FlywayApplicationService flywayApplicationService;

  @SpyBean
  private Clock clock;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldAddUserAuthorityChangelog() {
    // Given
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");

    // When
    flywayApplicationService.addUserAuthorityChangelog(project);

    // Then
    assertUserAuthoritySqlFile(project);
  }
}
