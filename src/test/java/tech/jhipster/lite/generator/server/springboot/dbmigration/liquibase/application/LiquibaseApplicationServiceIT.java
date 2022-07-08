package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain.Liquibase.*;

import java.time.Clock;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.postgresql.application.PostgresqlApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class LiquibaseApplicationServiceIT {

  @Autowired
  private SpringBootService springBootService;

  @Autowired
  private PostgresqlApplicationService postgresqlApplicationService;

  @Autowired
  private MySQLApplicationService mySQLApplicationService;

  @Autowired
  private MariaDBApplicationService mariaDBApplicationService;

  @Autowired
  private LiquibaseApplicationService liquibaseApplicationService;

  @SpyBean
  private Clock clock;

  @BeforeEach
  void setUp() {
    initClock(clock);
  }

  @Test
  void shouldInit() {
    Project project = tmpProjectBuilder().build();
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    TestJHipsterModules.applyMaven(project);
    springBootService.init(project);
    TestJHipsterModules.applyer().module(postgresqlApplicationService.build(properties)).properties(properties).slug("postgresql").apply();

    liquibaseApplicationService.init(project);

    assertDependencies(project);
    assertFilesLiquibaseChangelogMasterXml(project);
    assertFilesLiquibaseJava(project);
    assertLoggerInConfig(project);
  }

  @Test
  void shouldAddLiquibase() {
    Project project = tmpProjectBuilder().build();
    TestJHipsterModules.applyMaven(project);

    liquibaseApplicationService.addLiquibase(project);

    assertDependencies(project);
  }

  @Test
  void shouldAddChangelogMasterXml() {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);

    assertFilesLiquibaseChangelogMasterXml(project);
  }

  @Test
  void shouldAddChangelog() {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");

    String expected = "  <include file=\"config/liquibase/changelog/v1/master.xml\" relativeToChangelogFile=\"false\"/>";

    assertFileContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), List.of(expected, NEEDLE_LIQUIBASE));
  }

  @Test
  void shouldAddChangelogOnlyOneTime() throws Exception {
    Project project = tmpProject();

    liquibaseApplicationService.addChangelogMasterXml(project);
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");
    liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml");

    String expected = "  <include file=\"config/liquibase/changelog/v1/master.xml\" relativeToChangelogFile=\"false\"/>";

    assertFileContentManyTimes(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), expected, 1);
  }

  @Test
  void shouldNotAddChangelogWhenNoMasterXml() throws Exception {
    Project project = tmpProject();

    assertThatThrownBy(() -> liquibaseApplicationService.addChangelogXml(project, "v1", "master.xml"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAddConfigurationJava() {
    Project project = tmpProject();

    liquibaseApplicationService.addConfigurationJava(project);

    assertFilesLiquibaseJava(project);
  }

  @Test
  void shouldAddConfigurationJavaWithPackageName() {
    Project project = tmpProject();
    project.addConfig(PACKAGE_NAME, "tech.jhipster.lite");

    liquibaseApplicationService.addConfigurationJava(project);

    assertFilesLiquibaseJava(project);
  }

  @Test
  void shouldAddLoggingConfiguration() {
    Project project = tmpProject();
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    TestJHipsterModules.applyMaven(project);
    springBootService.init(project);
    TestJHipsterModules.applyer().module(postgresqlApplicationService.build(properties)).properties(properties).slug("postgresql").apply();

    liquibaseApplicationService.addLoggerInConfiguration(project);

    assertLoggerInConfig(project);
  }

  @Test
  @DisplayName("should add user and authority changelog for PostgreSQL")
  void shouldAddUserAuthorityChangelogForMyPostgreSQL() {
    Project project = tmpProjectWithLiquibaseMasterXml();

    liquibaseApplicationService.addUserAuthorityChangelog(project);

    assertFilesLiquibaseSqlUser(project);
    assertFileNoContent(
      project,
      getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_entity_User.xml"),
      "autoIncrement=\"true\""
    );
    assertFileExist(project, getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_sequence_User.xml"));
    assertFileContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), "20220128173026_added_sequence_User.xml");
  }

  @ParameterizedTest
  @EnumSource(value = DatabaseType.class, names = { "MYSQL", "MARIADB" })
  @DisplayName("should add user and authority changelog for MySQL or MariaDB")
  void shouldAddUserAuthorityChangelogForMySQLorMariaDB(DatabaseType databaseType) {
    Project project = tmpProject();
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    TestJHipsterModules.applyMaven(project);
    springBootService.init(project);
    if (databaseType.equals(DatabaseType.MYSQL)) {
      TestJHipsterModules.applyer().module(mySQLApplicationService.build(properties)).properties(properties).slug("mysql").apply();
    } else {
      TestJHipsterModules.applyer().module(mariaDBApplicationService.build(properties)).properties(properties).slug("mariadb").apply();
    }
    liquibaseApplicationService.init(project);

    liquibaseApplicationService.addUserAuthorityChangelog(project);

    assertFilesLiquibaseSqlUser(project);
    assertFileContent(
      project,
      getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_entity_User.xml"),
      "autoIncrement=\"true\""
    );
    assertFileNotExist(project, getPath(MAIN_RESOURCES, "config/liquibase/changelog/20220128173026_added_sequence_User.xml"));
    assertFileNoContent(project, getPath(MAIN_RESOURCES, "config/liquibase/master.xml"), "20220128173026_added_sequence_User.xml");
  }
}
