package tech.jhipster.lite.statistic.domain;

public interface StatisticsRepository {
  void save(AppliedModule moduleApplied);

  Statistics get();
}
