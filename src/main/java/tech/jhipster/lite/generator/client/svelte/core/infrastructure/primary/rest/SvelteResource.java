package tech.jhipster.lite.generator.client.svelte.core.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.client.svelte.core.application.SvelteApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

@RestController
@RequestMapping("/api/clients/svelte")
@Tag(name = "Svelte")
class SvelteResource {

  private final SvelteApplicationService svelteApplicationService;

  public SvelteResource(SvelteApplicationService svelteApplicationService) {
    this.svelteApplicationService = svelteApplicationService;
  }

  @Operation(summary = "Add Svelte", description = "Add Svelte")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Svelte")
  @PostMapping
  public void addSvelte(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    svelteApplicationService.addSvelte(project);
  }

  @Operation(summary = "Add SvelteKit with minimal CSS")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding SvelteKit with minimal CSS")
  @PostMapping("/styles")
  public void addStyledSvelteKit(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    svelteApplicationService.addStyledSvelteKit(project);
  }
}
