package tech.jhipster.lite.technical.infrastructure.primary;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/projects-test")
class ProjectFakeController {

  @PostMapping
  public void projectDto(@RequestBody ProjectDTO project) {}
}
