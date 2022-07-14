package tech.jhipster.lite.generator.server.springboot.user.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.generator.project.domain.DatabaseType.*;
import static tech.jhipster.lite.generator.server.springboot.user.application.SpringBootUserAssertFiles.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.database.mariadb.application.MariaDBApplicationService;
import tech.jhipster.lite.generator.server.springboot.database.mysql.application.MySQLApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootUserResourceIT {

  @Autowired
  private MySQLApplicationService mySQLApplicationService;

  @Autowired
  private MariaDBApplicationService mariaDBApplicationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("should add user and authority entities for PostgreSQL")
  void shouldAddUserAndAuthorityEntitiesForPostgreSQL() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/postgresql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, POSTGRESQL);
    assertFilesSqlJavaUserAuthority(project, POSTGRESQL);
    assertFilesSqlJavaAuditEntity(project, POSTGRESQL);

    checkSequence(project, POSTGRESQL);
  }

  @Test
  @DisplayName("should add user and authority entities for MySQL")
  void shouldAddUserAndAuthorityEntitiesForMySQL() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);
    TestJHipsterModules.applyer().module(mySQLApplicationService.build(properties)).properties(properties).slug("mysql").apply();

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/mysql")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, MYSQL);
    assertFilesSqlJavaUserAuthority(project, MYSQL);
    assertFilesSqlJavaAuditEntity(project, MYSQL);

    checkSequence(project, MYSQL);
  }

  @Test
  @DisplayName("should add user and authority entities for MariaDB")
  void shouldAddUserAndAuthorityEntitiesForMariaDB() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(project.getFolder())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);
    TestJHipsterModules.applyer().module(mariaDBApplicationService.build(properties)).properties(properties).slug("mariadb").apply();

    mockMvc
      .perform(
        post("/api/servers/spring-boot/features/user/mariadb")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertFilesSqlJavaUser(project, MARIADB);
    assertFilesSqlJavaUserAuthority(project, MARIADB);
    assertFilesSqlJavaAuditEntity(project, MARIADB);

    checkSequence(project, MARIADB);
  }
}
