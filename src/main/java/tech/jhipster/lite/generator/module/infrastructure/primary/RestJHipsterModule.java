package tech.jhipster.lite.generator.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.jhipster.lite.generator.module.domain.JHipsterModuleSlug;

@Schema(name = "JHipsterModule", description = "Information for a JHipster module")
class RestJHipsterModule {

  private final String slug;
  private final String description;

  private RestJHipsterModule(String slug, String description) {
    this.slug = slug;
    this.description = description;
  }

  static RestJHipsterModule from(JHipsterModuleSlug slug, String description) {
    return new RestJHipsterModule(slug.get(), description);
  }

  @Schema(description = "Module slug", required = true)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Module description", required = true)
  public String getDescription() {
    return description;
  }
}
