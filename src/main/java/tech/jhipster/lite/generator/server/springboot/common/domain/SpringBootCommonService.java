package tech.jhipster.lite.generator.server.springboot.common.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface SpringBootCommonService {
  boolean isSetWithMySQLOrMariaDBDatabase(Project project);

  boolean isDatabaseUseSequences(Project project);
}
