package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.Liquibase.*;

import java.time.Clock;
import tech.jhipster.lite.common.domain.TimeUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.generator.server.springboot.common.domain.Level;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

public class LiquibaseDomainService implements LiquibaseService {

  public static final String SOURCE = "server/springboot/dbmigration/liquibase";
  public static final String LIQUIBASE_PATH = "technical/infrastructure/secondary/liquibase";
  public static final String CONFIG_LIQUIBASE = "config/liquibase";
  public static final String CHANGELOG = CONFIG_LIQUIBASE + "/changelog";
  public static final String DATA = CONFIG_LIQUIBASE + "/data";

  private final ProjectRepository projectRepository;
  private final BuildToolService buildToolService;
  private final SpringBootCommonService springBootCommonService;
  private final Clock clock;

  public LiquibaseDomainService(
    ProjectRepository projectRepository,
    BuildToolService buildToolService,
    SpringBootCommonService springBootCommonService,
    Clock clock
  ) {
    this.projectRepository = projectRepository;
    this.buildToolService = buildToolService;
    this.springBootCommonService = springBootCommonService;
    this.clock = clock;
  }

  @Override
  public void init(Project project) {
    addLiquibase(project);
    addChangelogMasterXml(project);
    addConfigurationJava(project);
    addLoggerInConfiguration(project);
  }

  @Override
  public void addLiquibase(Project project) {
    buildToolService
      .getVersion(project, "liquibase")
      .ifPresentOrElse(
        version -> buildToolService.addProperty(project, "liquibase.version", version),
        () -> {
          throw new GeneratorException("Version not found: liquibase");
        }
      );
    Dependency liquibaseDependency = Dependency
      .builder()
      .groupId("org.liquibase")
      .artifactId("liquibase-core")
      .version("\\${liquibase.version}")
      .build();
    buildToolService.addDependency(project, liquibaseDependency);

    Dependency h2dependency = Dependency.builder().groupId("com.h2database").artifactId("h2").scope("test").build();
    buildToolService.addDependency(project, h2dependency);
  }

  @Override
  public void addChangelogMasterXml(Project project) {
    projectRepository.add(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, "resources"), LIQUIBASE_MASTER_XML)
        .withDestinationFolder(getPath(MAIN_RESOURCES, CONFIG_LIQUIBASE))
    );
  }

  @Override
  public void addChangelogXml(Project project, String path, String fileName) {
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

  @Override
  public void addConfigurationJava(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToLiquibase(project, packageNamePath, "src", "AsyncSpringLiquibase.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "LiquibaseConfiguration.java", MAIN_JAVA);
    templateToLiquibase(project, packageNamePath, "src", "SpringLiquibaseUtil.java", MAIN_JAVA);

    springBootCommonService.addTestLogbackRecorder(project);
    templateToLiquibase(project, packageNamePath, "test", "AsyncSpringLiquibaseTest.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "LiquibaseConfigurationIT.java", TEST_JAVA);
    templateToLiquibase(project, packageNamePath, "test", "SpringLiquibaseUtilTest.java", TEST_JAVA);
  }

  @Override
  public void addLoggerInConfiguration(Project project) {
    addLogger(project, "liquibase", Level.WARN);
    addLogger(project, "LiquibaseSchemaResolver", Level.INFO);
    addLogger(project, "com.zaxxer.hikari", Level.WARN);
  }

  public void addLogger(Project project, String packageName, Level level) {
    springBootCommonService.addLogger(project, packageName, level);
    springBootCommonService.addLoggerTest(project, packageName, level);
  }

  private void templateToLiquibase(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, type), sourceFilename)
        .withDestinationFolder(getPath(destination, source, LIQUIBASE_PATH))
    );
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

  private String getUserResourcePath() {
    return getPath(SOURCE, "resources/user");
  }

  private boolean isDatabaseWhichNeedsSequenceStrategy(Project project) {
    return !springBootCommonService.isSetWithMySQLOrMariaDBDatabase(project);
  }
}
