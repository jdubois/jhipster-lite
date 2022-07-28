package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.Liquibase.*;

import java.time.Clock;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.common.domain.TimeUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class LiquibaseDomainService implements LiquibaseService {

  public static final String SOURCE = "server/springboot/dbmigration/liquibase";
  public static final String LIQUIBASE_PATH = "technical/infrastructure/secondary/liquibase";
  public static final String CONFIG_LIQUIBASE = "config/liquibase";
  public static final String CHANGELOG = CONFIG_LIQUIBASE + "/changelog";
  public static final String DATA = CONFIG_LIQUIBASE + "/data";

  private final ProjectRepository projectRepository;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public LiquibaseDomainService(ProjectRepository projectRepository, SpringBootCommonService springBootCommonService, Clock clock) {
    this.projectRepository = projectRepository;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Override
  public void addUserAuthorityChangelog(Project project) {
    addSqlSequenceUserChangelog(project);
    addSqlUserChangelog(project);
    addSqlUserAuthorityChangelog(project);
  }

  private void addSqlSequenceUserChangelog(Project project) {
    if (isDatabaseWhichNeedsSequenceStrategy(project)) {
      String sequenceUserChangelog = TimeUtils.getDateString(clock) + "_added_sequence_User.xml";
      addChangelogXml(project, "", sequenceUserChangelog);
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(getUserResourcePath(), "sequence_user.xml")
          .withDestination(getPath(MAIN_RESOURCES, CHANGELOG), sequenceUserChangelog)
      );
    }
  }

  private void addSqlUserChangelog(Project project) {
    String userChangelog = TimeUtils.getDateString(clock) + "_added_entity_User.xml";
    addChangelogXml(project, "", userChangelog);
    String userXmlFile = "user.xml";
    if (springBootCommonService.isSetWithMySQLOrMariaDBDatabase(project)) {
      userXmlFile = "user_with_autoincrement.xml";
    }
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getUserResourcePath(), userXmlFile)
        .withDestination(getPath(MAIN_RESOURCES, CHANGELOG), userChangelog)
    );

    projectRepository.add(
      ProjectFile.forProject(project).withSource(getUserResourcePath(), "user.csv").withDestinationFolder(getPath(MAIN_RESOURCES, DATA))
    );
  }

  private void addSqlUserAuthorityChangelog(Project project) {
    // Update liquibase master file
    String authorityChangelog = TimeUtils.getDateString(clock) + "_added_entity_Authority.xml";
    addChangelogXml(project, "", authorityChangelog);

    // Copy liquibase files
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getUserResourcePath(), "authority.xml")
        .withDestination(getPath(MAIN_RESOURCES, CHANGELOG), authorityChangelog)
    );
    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getUserResourcePath(), "authority.csv")
        .withDestinationFolder(getPath(MAIN_RESOURCES, DATA))
    );

    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getUserResourcePath(), "user_authority.csv")
        .withDestinationFolder(getPath(MAIN_RESOURCES, DATA))
    );
  }

  @Generated(reason = "Candidate for deletion and the missing test is an implementation detail")
  private void addChangelogXml(Project project, String path, String fileName) {
    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(2);

    String includeLine = new StringBuilder()
      .append(Liquibase.getIncludeLine(path, fileName))
      .append(LF)
      .append(indent(1, indent))
      .append(NEEDLE_LIQUIBASE)
      .toString();

    if (!projectRepository.containsRegexp(project, getPath(MAIN_RESOURCES, CONFIG_LIQUIBASE), LIQUIBASE_MASTER_XML, includeLine)) {
      projectRepository.replaceText(
        project,
        getPath(MAIN_RESOURCES, CONFIG_LIQUIBASE),
        LIQUIBASE_MASTER_XML,
        NEEDLE_LIQUIBASE,
        includeLine
      );
    }
  }

  private String getUserResourcePath() {
    return getPath(SOURCE, "resources/user");
  }

  private boolean isDatabaseWhichNeedsSequenceStrategy(Project project) {
    return !springBootCommonService.isSetWithMySQLOrMariaDBDatabase(project);
  }
}
