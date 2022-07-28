package tech.jhipster.lite.generator.client.angular.security.jwt.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.angular.security.jwt.application.AngularJwtApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/clients/angular/jwt")
@Tag(name = "Angular")
class AngularJwtResource {

  private final AngularJwtApplicationService angularJwtApplicationService;

  public AngularJwtResource(AngularJwtApplicationService angularJwtApplicationService) {
    this.angularJwtApplicationService = angularJwtApplicationService;
  }

  @Operation(summary = "Add Angular with authentication JWT")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Angular with authentication JWT")
  @PostMapping
  public void addJwtAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularJwtApplicationService.addJwtAngular(project);
  }
}
