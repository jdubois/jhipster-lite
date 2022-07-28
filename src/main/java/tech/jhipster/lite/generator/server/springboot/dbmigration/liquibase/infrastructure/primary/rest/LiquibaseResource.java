package tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.dbmigration.liquibase.application.LiquibaseApplicationService;

@RestController
@RequestMapping("/api/servers/spring-boot/database-migration-tools/liquibase")
@Tag(name = "Spring Boot - Database Migration")
class LiquibaseResource {

  private final LiquibaseApplicationService liquibaseApplicationService;

  public LiquibaseResource(LiquibaseApplicationService liquibaseApplicationService) {
    this.liquibaseApplicationService = liquibaseApplicationService;
  }

  @Operation(summary = "Add User and Authority changelogs")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding changelogs for user and authority")
  @PostMapping("user")
  public void addUserAndAuthority(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    liquibaseApplicationService.addUserAuthorityChangelog(project);
  }
}
