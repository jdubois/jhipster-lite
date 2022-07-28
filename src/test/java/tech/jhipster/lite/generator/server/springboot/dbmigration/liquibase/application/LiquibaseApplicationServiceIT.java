package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseAssertFiles.*;

import java.time.Clock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class LiquibaseApplicationServiceIT {

  @Autowired
  private LiquibaseApplicationService liquibaseService;

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
    TestJHipsterModules.applySpringBootCore(project);
    if (databaseType.equals(DatabaseType.MYSQL)) {
      TestJHipsterModules.applyer().module(mySQLApplicationService.build(properties)).properties(properties).slug("mysql").apply();
    } else {
      TestJHipsterModules.applyer().module(mariaDBApplicationService.build(properties)).properties(properties).slug("mariadb").apply();
    }

    TestJHipsterModules.applyer().module(liquibaseService.buildModule(properties)).properties(properties).slug("liquibase").apply();

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
