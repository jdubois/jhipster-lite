package tech.jhipster.lite.statistic.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import tech.jhipster.lite.statistic.domain.Statistics;

@Schema(name = "statistics", description = "JHipster lite usage statistics")
class RestStatistics {

  private final long appliedModules;

  private RestStatistics(long appliedModules) {
    this.appliedModules = appliedModules;
  }

  public static RestStatistics from(Statistics statistics) {
    return new RestStatistics(statistics.appliedModules());
  }

  @Schema(description = "Number of module applied on this instance", requiredMode = RequiredMode.REQUIRED)
  public long getAppliedModules() {
    return appliedModules;
  }
}
