package tech.jhipster.lite.statistic.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.statistic.domain.AppliedModule;
import tech.jhipster.lite.statistic.domain.Statistics;
import tech.jhipster.lite.statistic.domain.StatisticsRepository;

@Service
public class StatisticsApplicationService {

  private final StatisticsRepository statistics;

  public StatisticsApplicationService(StatisticsRepository statistics) {
    this.statistics = statistics;
  }

  public void moduleApplied(AppliedModule moduleApplied) {
    statistics.save(moduleApplied);
  }

  public Statistics get() {
    return statistics.get();
  }
}
