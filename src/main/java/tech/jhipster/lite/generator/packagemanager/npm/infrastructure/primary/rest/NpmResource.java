package tech.jhipster.lite.generator.packagemanager.npm.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.packagemanager.npm.application.NpmApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/package-managers/npm")
@Tag(name = "NPM")
class NpmResource {

  private final NpmApplicationService npmApplicationService;

  public NpmResource(NpmApplicationService npmApplicationService) {
    this.npmApplicationService = npmApplicationService;
  }

  @Operation(summary = "Run: npm install")
  @ApiResponse(responseCode = "500", description = "An error occurred while installing project")
  @PostMapping("/install")
  public void install(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    npmApplicationService.install(project);
  }

  @Operation(summary = "Run: npm run prettier:format")
  @ApiResponse(responseCode = "500", description = "An error occurred while prettifying project")
  @PostMapping("/prettier-format")
  public void prettify(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    npmApplicationService.prettify(project);
  }
}
