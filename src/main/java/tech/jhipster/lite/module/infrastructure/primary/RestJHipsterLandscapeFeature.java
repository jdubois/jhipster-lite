package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeElementType;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeFeature;

@JsonPropertyOrder({ "type", "slug", "modules" })
@Schema(name = "JHipsterLandscapeFeature", description = "Feature in a module landscape")
final class RestJHipsterLandscapeFeature implements RestJHipsterLandscapeElement {

  private final String slug;
  private final Collection<RestJHipsterLandscapeModule> modules;

  private RestJHipsterLandscapeFeature(String slug, Collection<RestJHipsterLandscapeModule> modules) {
    this.slug = slug;
    this.modules = modules;
  }

  static RestJHipsterLandscapeFeature fromFeature(JHipsterLandscapeFeature feature) {
    return new RestJHipsterLandscapeFeature(
      feature.slug().get(),
      feature.modules().stream().map(RestJHipsterLandscapeModule::fromModule).toList()
    );
  }

  @Override
  @Schema(description = "Type of this landscape element", required = true)
  public JHipsterLandscapeElementType getType() {
    return JHipsterLandscapeElementType.FEATURE;
  }

  @Schema(description = "Unique slug of this feature", required = true)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Modules in this feature", required = true)
  public Collection<RestJHipsterLandscapeModule> getModules() {
    return modules;
  }
}
