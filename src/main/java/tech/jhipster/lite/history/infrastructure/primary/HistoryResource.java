package tech.jhipster.lite.history.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.jhipster.lite.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.history.infrastructure.primary.dto.HistoryDTO;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@RestController
@RequestMapping("/api/project-histories")
@Tag(name = "History")
class HistoryResource {

  private final GeneratorHistoryApplicationService generatorHistoryApplicationService;

  public HistoryResource(GeneratorHistoryApplicationService generatorHistoryApplicationService) {
    this.generatorHistoryApplicationService = generatorHistoryApplicationService;
  }

  @Operation(summary = "Get project service Id history")
  @ApiResponse(responseCode = "500", description = "An error occurred while getting service id history project")
  @GetMapping("/serviceIds")
  public ResponseEntity<HistoryDTO> serviceIds(
    @Parameter(description = "Project path to get history") @RequestParam(value = "folder") String folder
  ) {
    return ResponseEntity.ok(HistoryDTO.from(generatorHistoryApplicationService.getValues(new JHipsterProjectFolder(folder))));
  }

  @Operation(summary = "Get project history")
  @ApiResponse(responseCode = "500", description = "An error occurred while getting history project")
  @GetMapping
  public ResponseEntity<List<GeneratorHistoryValue>> history(
    @Parameter(description = "Project path to get history") @RequestParam(value = "folder") String folder
  ) {
    return ResponseEntity.ok(generatorHistoryApplicationService.getValues(new JHipsterProjectFolder(folder)));
  }
}
