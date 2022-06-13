package tech.jhipster.lite.generator.client.angular.security.oauth2.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.angular.security.oauth2.application.AngularOauth2ApplicationService;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/angular/oauth2")
@Tag(name = "Angular")
class AngularOauth2Resource {

  private final AngularOauth2ApplicationService angularOauth2ApplicationService;

  public AngularOauth2Resource(AngularOauth2ApplicationService angularOauth2ApplicationService) {
    this.angularOauth2ApplicationService = angularOauth2ApplicationService;
  }

  @Operation(summary = "Add OAuth2 authentication")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding OAuth2 authentication")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.ANGULAR_OAUTH2)
  public void addJwtAngular(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    angularOauth2ApplicationService.addOauth2(project);
  }
}
