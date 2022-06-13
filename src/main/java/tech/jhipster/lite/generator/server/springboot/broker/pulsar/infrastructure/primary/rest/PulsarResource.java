package tech.jhipster.lite.generator.server.springboot.broker.pulsar.infrastructure.primary.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.generator.project.domain.GeneratorAction;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.application.PulsarApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/brokers/pulsar")
@Tag(name = "Spring Boot - Broker")
class PulsarResource {

  private final PulsarApplicationService pulsarApplicationService;

  public PulsarResource(PulsarApplicationService pulsarApplicationService) {
    this.pulsarApplicationService = pulsarApplicationService;
  }

  @Operation(summary = "Add Pulsar dependencies, with testcontainers")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Pulsar")
  @PostMapping
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_PULSAR)
  public void init(final @RequestBody ProjectDTO projectDTO) {
    final Project project = ProjectDTO.toProject(projectDTO);
    pulsarApplicationService.init(project);
  }
}
