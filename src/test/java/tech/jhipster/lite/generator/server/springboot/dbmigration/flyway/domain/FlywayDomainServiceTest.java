package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.project.domain.FilePath;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FlywayDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private SpringBootCommonService springBootCommonService;

  @Spy
  private Clock clock = Clock.fixed(Instant.parse("2022-01-22T14:01:54.954396664Z"), ZoneId.of("Europe/Paris"));

  @InjectMocks
  private FlywayDomainService flywayDomainService;

  @Test
  void shouldAddUserAuthorityChangelogWithSequence() {
    // Given
    Project project = tmpProject();

    when(springBootCommonService.isDatabaseUseSequences(project)).thenReturn(true);

    // When
    flywayDomainService.addUserAuthorityChangelog(project);

    // Then
    verify(projectRepository)
      .add(
        projectFileArgument(
          project,
          new FilePath(
            "server/springboot/dbmigration/flyway/resources/user",
            "V00000000000000__create_user_authority_tables_postgresql.sql"
          ),
          new FilePath("src/main/resources/db/migration", "V20220122150155__create_user_authority_tables.sql")
        )
      );
  }

  @Test
  void shouldAddUserAuthorityChangelogWithoutSequence() {
    // Given
    Project project = tmpProject();

    when(springBootCommonService.isDatabaseUseSequences(project)).thenReturn(false);

    // When
    flywayDomainService.addUserAuthorityChangelog(project);

    // ThenO
    verify(projectRepository)
      .add(
        projectFileArgument(
          project,
          new FilePath("server/springboot/dbmigration/flyway/resources/user", "V00000000000000__create_user_authority_tables.sql"),
          new FilePath("src/main/resources/db/migration", "V20220122150155__create_user_authority_tables.sql")
        )
      );
  }
}
