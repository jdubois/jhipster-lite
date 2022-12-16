package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeDependency;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeElementType;

@Schema(name = "JHipsterLandscapeDependency", description = "A dependency to another element")
class RestJHipsterLandscapeDependency {

  private final JHipsterLandscapeElementType type;
  private final String slug;

  private RestJHipsterLandscapeDependency(JHipsterLandscapeElementType type, String slug) {
    this.type = type;
    this.slug = slug;
  }

  public static RestJHipsterLandscapeDependency from(JHipsterLandscapeDependency dependency) {
    return new RestJHipsterLandscapeDependency(dependency.type(), dependency.slug().get());
  }

  @Schema(description = "Type of this dependency", requiredMode = RequiredMode.REQUIRED)
  public JHipsterLandscapeElementType getType() {
    return type;
  }

  @Schema(description = "Slug of this dependency", requiredMode = RequiredMode.REQUIRED)
  public String getSlug() {
    return slug;
  }
}
