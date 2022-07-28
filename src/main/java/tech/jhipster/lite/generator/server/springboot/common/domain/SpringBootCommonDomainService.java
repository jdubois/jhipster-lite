package tech.jhipster.lite.generator.server.springboot.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.Optional;
import java.util.function.Function;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;

public class SpringBootCommonDomainService implements SpringBootCommonService {

  private static final String PROJECT_FIELD_ASSERT_NAME = "project";

  @Override
  public boolean isSetWithMySQLOrMariaDBDatabase(Project project) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);
    return isMariaDBDatabase(project) || isMySQLDatabase(project);
  }

  @Override
  public boolean isDatabaseUseSequences(Project project) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);

    return hasSpecificDatabaseProperty(project, DatabaseType.POSTGRESQL);
  }

  private boolean isMySQLDatabase(Project project) {
    return hasSpecificDatabaseProperty(project, DatabaseType.MYSQL);
  }

  private boolean isMariaDBDatabase(Project project) {
    return hasSpecificDatabaseProperty(project, DatabaseType.MARIADB);
  }

  private boolean hasSpecificDatabaseProperty(Project project, DatabaseType databaseType) {
    return getProperty(project, "spring.datasource.url").filter(value -> value.contains(databaseType.id())).isPresent();
  }

  private Optional<String> getProperty(Project project, String key) {
    Assert.notNull(PROJECT_FIELD_ASSERT_NAME, project);
    Assert.notBlank("key", key);

    return FileUtils
      .readLine(getPath(project.getFolder(), MAIN_RESOURCES, CONFIG, "application.properties"), key + "=")
      .map(toPropertyValue());
  }

  private Function<String, String> toPropertyValue() {
    return readValue -> readValue.split("=")[1];
  }
}
