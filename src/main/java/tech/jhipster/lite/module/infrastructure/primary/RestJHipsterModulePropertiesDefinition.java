package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;

@Schema(name = "JHipsterModulePropertiesDefinition", description = "Definition for module properties")
class RestJHipsterModulePropertiesDefinition {

  private final Collection<RestJHipsterModulePropertyDefinition> definitions;

  private RestJHipsterModulePropertiesDefinition(Collection<RestJHipsterModulePropertyDefinition> definitions) {
    this.definitions = definitions;
  }

  static RestJHipsterModulePropertiesDefinition from(JHipsterModulePropertiesDefinition propertiesDefinition) {
    return new RestJHipsterModulePropertiesDefinition(
      propertiesDefinition.stream().map(RestJHipsterModulePropertyDefinition::from).toList()
    );
  }

  @Schema(description = "Definition of the properties for a module")
  public Collection<RestJHipsterModulePropertyDefinition> getDefinitions() {
    return definitions;
  }
}
