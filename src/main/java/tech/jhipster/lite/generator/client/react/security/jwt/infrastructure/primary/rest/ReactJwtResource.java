package tech.jhipster.lite.generator.client.react.security.jwt.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.react.security.jwt.application.ReactJwtApplicationService;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/clients/react/jwt")
@Tag(name = "React")
class ReactJwtResource {

  private final ReactJwtApplicationService reactJwtApplicationService;

  public ReactJwtResource(ReactJwtApplicationService reactJwtApplicationService) {
    this.reactJwtApplicationService = reactJwtApplicationService;
  }

  @Operation(summary = "Add JWT Login React", description = "Add JWT Login React")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding JWT Login React")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.REACT_JWT)
  public void addLoginReact(@RequestBody final ProjectDTO projectDTO) {
    var project = ProjectDTO.toProject(projectDTO);
    reactJwtApplicationService.addLoginReact(project);
  }
}
