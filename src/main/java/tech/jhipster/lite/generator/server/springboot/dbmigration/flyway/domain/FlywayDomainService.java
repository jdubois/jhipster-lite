package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.time.Clock;
import tech.jhipster.lite.common.domain.TimeUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class FlywayDomainService implements FlywayService {

  private static final String DEFAULT_SQL_FILES_FOLDER = "db/migration";
  private static final String SQL_INIT_FILE_SOURCE = "server/springboot/dbmigration/flyway";
  private static final String SQL_USER_AUTHORITY_FILE_NAME = "V%s__create_user_authority_tables.sql";

  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public FlywayDomainService(ProjectRepository projectRepository, SpringBootCommonService springBootCommonService, Clock clock) {
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Override
  public void addUserAuthorityChangelog(Project project) {
    String sqlFileSource = buildSqlFileSource(project);

    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SQL_INIT_FILE_SOURCE, "resources/user"), sqlFileSource)
        .withDestination(getPath(MAIN_RESOURCES, DEFAULT_SQL_FILES_FOLDER), buildFileNameWithTimestamp(SQL_USER_AUTHORITY_FILE_NAME, 1))
    );
  }

  private String buildSqlFileSource(Project project) {
    String sqlFileSourcePrefix = "V00000000000000__create_user_authority_tables";
    if (isDatabaseUseSequences(project)) {
      return sqlFileSourcePrefix + "_postgresql.sql";
    }
    return sqlFileSourcePrefix + ".sql";
  }

  private String buildFileNameWithTimestamp(String fileNameFormat, int idxScript) {
    return fileNameFormat.formatted(TimeUtils.getDateString(clock, idxScript));
  }

  private boolean isDatabaseUseSequences(Project project) {
    return springBootCommonService.isDatabaseUseSequences(project);
  }
}
