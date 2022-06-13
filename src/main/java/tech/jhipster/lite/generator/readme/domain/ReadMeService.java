package tech.jhipster.lite.generator.readme.domain;

import tech.jhipster.lite.generator.project.domain.Project;

public interface ReadMeService {
  void addSection(Project project, String header, String body);
}
