package tech.jhipster.lite.generator.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

class RestJHipsterModuleProperties {

  private final String projectFolder;
  private final Map<String, Object> properties;

  RestJHipsterModuleProperties(
    @JsonProperty("projectFolder") String projectFolder,
    @JsonProperty("properties") Map<String, Object> properties
  ) {
    this.projectFolder = projectFolder;
    this.properties = properties;
  }

  public JHipsterModuleProperties toDomain() {
    return new JHipsterModuleProperties(projectFolder, properties);
  }
}
