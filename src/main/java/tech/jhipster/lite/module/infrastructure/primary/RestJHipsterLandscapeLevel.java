package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.Collection;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeLevel;

@Schema(name = "JHipsterLandscapeLevel", description = "One level in the module landscape")
class RestJHipsterLandscapeLevel {

  private final Collection<RestJHipsterLandscapeElement> elements;

  private RestJHipsterLandscapeLevel(Collection<RestJHipsterLandscapeElement> elements) {
    this.elements = elements;
  }

  static RestJHipsterLandscapeLevel from(JHipsterLandscapeLevel level) {
    return new RestJHipsterLandscapeLevel(level.elements().stream().map(RestJHipsterLandscapeElement::from).toList());
  }

  @Schema(description = "Elements in this level", requiredMode = RequiredMode.REQUIRED)
  public Collection<RestJHipsterLandscapeElement> getElements() {
    return elements;
  }
}
