package tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.dbmigration.flyway.application.FlywayApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/database-migration-tools/flyway")
@Tag(name = "Spring Boot - Database Migration")
class FlywayResource {

  private final FlywayApplicationService flywayApplicationService;

  public FlywayResource(FlywayApplicationService flywayApplicationService) {
    this.flywayApplicationService = flywayApplicationService;
  }

  @Operation(summary = "Add User and Authority changelogs")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding changelogs for user and authority")
  @PostMapping("/user")
  public void addUserAndAuthority(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    flywayApplicationService.addUserAuthorityChangelog(project);
  }
}
