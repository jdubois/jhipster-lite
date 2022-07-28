package tech.jhipster.lite.project.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.jhipster.lite.project.domain.history.ProjectAction;

@Schema(name = "appliedModule", description = "Information for an applied module")
class RestAppliedModule {

  private final String slug;

  private RestAppliedModule(String slug) {
    this.slug = slug;
  }

  static RestAppliedModule from(ProjectAction action) {
    return new RestAppliedModule(action.module().get());
  }

  @Schema(description = "Slug of the applied module", required = true)
  public String getSlug() {
    return slug;
  }
}
