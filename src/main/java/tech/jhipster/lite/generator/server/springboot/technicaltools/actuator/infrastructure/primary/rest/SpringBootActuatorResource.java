package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.infrastructure.primary.rest;

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
import tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.application.SpringBootActuatorApplicationService;
import tech.jhipster.lite.technical.infrastructure.primary.annotation.GeneratorStep;

@RestController
@RequestMapping("/api/servers/spring-boot/technical-tools")
@Tag(name = "Spring Boot - Technical tools")
class SpringBootActuatorResource {

  private final SpringBootActuatorApplicationService springBootActuatorApplicationService;

  public SpringBootActuatorResource(SpringBootActuatorApplicationService springBootActuatorApplicationService) {
    this.springBootActuatorApplicationService = springBootActuatorApplicationService;
  }

  @Operation(summary = "Add Spring Boot Actuator")
  @ApiResponse(responseCode = "500", description = "An error occurred while adding Spring Boot Actuator")
  @PostMapping("/actuator")
  @GeneratorStep(id = GeneratorAction.SPRINGBOOT_ACTUATOR)
  public void addActuator(@RequestBody ProjectDTO projectDTO) {
    Project project = ProjectDTO.toProject(projectDTO);
    springBootActuatorApplicationService.addActuator(project);
  }
}
