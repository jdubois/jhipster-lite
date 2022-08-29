package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscape;

@Schema(name = "JHipsterLandscape", description = "Landscape of modules in JHipster")
class RestJHipsterLandscape {

  private final Collection<RestJHipsterLandscapeLevel> levels;

  private RestJHipsterLandscape(Collection<RestJHipsterLandscapeLevel> levels) {
    this.levels = levels;
  }

  static RestJHipsterLandscape from(JHipsterLandscape landscape) {
    return new RestJHipsterLandscape(landscape.levels().stream().map(RestJHipsterLandscapeLevel::from).toList());
  }

  @Schema(description = "Levels in the landscape", required = true)
  public Collection<RestJHipsterLandscapeLevel> getLevels() {
    return levels;
  }
}
