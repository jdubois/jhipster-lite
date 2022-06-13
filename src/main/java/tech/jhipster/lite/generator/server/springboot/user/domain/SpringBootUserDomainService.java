package tech.jhipster.lite.generator.server.springboot.user.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.generator.project.domain.DatabaseType;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringBootUserDomainService implements SpringBootUserService {

  private static final String SOURCE = "server/springboot/user/sqldatabase";
  private static final String USER_CONTEXT = "user";
  private static final String USER_INFRASTRUCTURE_SECONDARY = getPath(USER_CONTEXT, INFRASTRUCTURE_SECONDARY);
  private static final String USER_DOMAIN = getPath(USER_CONTEXT, DOMAIN);
  private static final String USER_DATABASE_KEY = "sqlDatabaseName";

  private final ProjectRepository projectRepository;

  public SpringBootUserDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addUserAndAuthorityEntities(Project project, DatabaseType sqlDatabase) {
    addUserEntity(project, sqlDatabase);
    addAuthorityEntity(project, sqlDatabase);
    addAbstractAuditingEntity(project, sqlDatabase);
  }

  private void addUserEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    if (isDatabaseWhichNoNeedsSequenceStrategy(sqlDatabase)) {
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "UserEntity_without_sequence.java")
          .withDestination(getSqlJavaPath(packageNamePath, sqlDatabase), "UserEntity.java")
      );
    } else {
      projectRepository.template(
        ProjectFile
          .forProject(project)
          .withSource(SOURCE, "UserEntity.java")
          .withDestinationFolder(getSqlJavaPath(packageNamePath, sqlDatabase))
      );
    }
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "UserJpaRepository.java")
        .withDestinationFolder(getSqlJavaPath(packageNamePath, sqlDatabase))
    );
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "UserConstants.java").withDestinationFolder(getSqlDomainJavaPath(packageNamePath))
    );

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "UserEntityTest.java")
        .withDestinationFolder(getSqlJavaTestPath(packageNamePath, sqlDatabase))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "UserJpaRepositoryIT.java")
        .withDestinationFolder(getSqlJavaTestPath(packageNamePath, sqlDatabase))
    );
  }

  private void addAuthorityEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "AuthorityEntity.java")
        .withDestinationFolder(getSqlJavaPath(packageNamePath, sqlDatabase))
    );
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "AuthorityJpaRepository.java")
        .withDestinationFolder(getSqlJavaPath(packageNamePath, sqlDatabase))
    );

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "AuthorityEntityTest.java")
        .withDestinationFolder(getSqlJavaTestPath(packageNamePath, sqlDatabase))
    );
  }

  private void addAbstractAuditingEntity(Project project, DatabaseType sqlDatabase) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath(PACKAGE_PATH));
    project.addConfig(USER_DATABASE_KEY, sqlDatabase.id());

    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(SOURCE, "AbstractAuditingEntity.java")
        .withDestinationFolder(getSqlJavaPath(packageNamePath, sqlDatabase))
    );
  }

  private String getSqlJavaPath(String packageNamePath, DatabaseType sqlDatabase) {
    return getPath(MAIN_JAVA, packageNamePath, USER_INFRASTRUCTURE_SECONDARY + "/" + sqlDatabase.id());
  }

  private String getSqlDomainJavaPath(String packageNamePath) {
    return getPath(MAIN_JAVA, packageNamePath, USER_DOMAIN);
  }

  private String getSqlJavaTestPath(String packageNamePath, DatabaseType sqlDatabase) {
    return getPath(TEST_JAVA, packageNamePath, USER_INFRASTRUCTURE_SECONDARY + "/" + sqlDatabase.id());
  }

  private boolean isMySQLDatabase(DatabaseType databaseType) {
    return databaseType.equals(DatabaseType.MYSQL);
  }

  private boolean isMariaDBDatabase(DatabaseType databaseType) {
    return databaseType.equals(DatabaseType.MARIADB);
  }

  private boolean isDatabaseWhichNoNeedsSequenceStrategy(DatabaseType databaseType) {
    return isMySQLDatabase(databaseType) || isMariaDBDatabase(databaseType);
  }
}
