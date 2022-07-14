package tech.jhipster.lite.generator.project.infrastructure.primary.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.module.domain.JHipsterProjectFolderFactory;

@RestController
@RequestMapping("/api/project-folders")
class ProjectFoldersResource {

  private final JHipsterProjectFolderFactory jHipsterProjectFolderFactory;

  ProjectFoldersResource(JHipsterProjectFolderFactory jHipsterProjectFolderFactory) {
    this.jHipsterProjectFolderFactory = jHipsterProjectFolderFactory;
  }

  @GetMapping
  public String getAvailableFolderName() {
    return jHipsterProjectFolderFactory.generatePath();
  }
}
