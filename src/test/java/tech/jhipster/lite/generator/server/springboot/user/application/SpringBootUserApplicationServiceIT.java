package tech.jhipster.lite.generator.server.springboot.user.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.*;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.application.SpringBootApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class SpringBootUserApplicationServiceIT {

  @Autowired
  private SpringBootApplicationService springBootApplicationService;

  @Autowired
  private MySQLApplicationService mySQLApplicationService;

  @Autowired
  private MariaDBApplicationService mariaDBApplicationService;

  @Autowired
  private SpringBootUserApplicationService springBootUserApplicationService;

  @Test
  void shouldAddUserAndAuthorityEntitiesForPostgresql() {
    Project project = tmpProjectBuilder().build();

    springBootUserApplicationService.addUserAndAuthorityEntities(project, POSTGRESQL);

    assertFilesSqlJavaUser(project, POSTGRESQL);
    assertFilesSqlJavaUserAuthority(project, POSTGRESQL);
    assertFilesSqlJavaAuditEntity(project, POSTGRESQL);

    checkSequence(project, POSTGRESQL);
  }

  @ParameterizedTest
  @EnumSource(value = DatabaseType.class, names = { "MYSQL", "MARIADB" })
  void shouldAddUserAndAuthorityEntitiesForMysqlOrMariaDB(DatabaseType databaseType) {
    Project project = tmpProject();
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    TestJHipsterModules.applyMaven(project);
    springBootApplicationService.init(project);
    if (databaseType.equals(MYSQL)) {
      TestJHipsterModules.applyer().module(mySQLApplicationService.build(properties)).properties(properties).slug("mysql").apply();
    } else {
      TestJHipsterModules.applyer().module(mariaDBApplicationService.build(properties)).properties(properties).slug("mariadb").apply();
    }

    springBootUserApplicationService.addUserAndAuthorityEntities(project, databaseType);

    assertFilesSqlJavaUser(project, databaseType);
    assertFilesSqlJavaUserAuthority(project, databaseType);
    assertFilesSqlJavaAuditEntity(project, databaseType);

    checkSequence(project, databaseType);
  }
}
