package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Modules")
@RequestMapping("/api/modules")
class ModulesResource {

  private final JHipsterModulesResources modulesResources;

  public ModulesResource(JHipsterModulesResources modulesResources) {
    this.modulesResources = modulesResources;
  }

  @GetMapping
  @Operation(summary = "List available modules")
  public ResponseEntity<RestJHipsterModules> listModules() {
    return ResponseEntity.ok(RestJHipsterModules.from(modulesResources));
  }
}
