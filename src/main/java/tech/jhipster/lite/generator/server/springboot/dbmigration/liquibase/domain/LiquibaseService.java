package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface LiquibaseService {
  void addUserAuthorityChangelog(Project project);
}
