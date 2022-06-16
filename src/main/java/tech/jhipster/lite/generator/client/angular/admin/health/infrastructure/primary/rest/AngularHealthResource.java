package tech.jhipster.lite.generator.client.angular.admin.health.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.angular.admin.health.application.AngularHealthApplicationService;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/angular/admin-pages/health")
@Tag(name = "Angular")
class AngularHealthResource {

  private final AngularHealthApplicationService angularHealthApplicationService;

  public AngularHealthResource(AngularHealthApplicationService angularHealthApplicationService) {
    this.angularHealthApplicationService = angularHealthApplicationService;
  }

  @Operation(summary = "Angular Health")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Angular Health")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.ANGULAR_HEALTH)
  public void addHealthAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularHealthApplicationService.addHealthAngular(project);
  }
}
