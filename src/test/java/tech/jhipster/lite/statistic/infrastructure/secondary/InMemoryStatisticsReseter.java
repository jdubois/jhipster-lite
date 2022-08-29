package tech.jhipster.lite.statistic.infrastructure.secondary;

import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class InMemoryStatisticsReseter {

  @Autowired
  private InMemoryStatisticsRepository statistics;

  @Before
  public void resetStatistics() {
    statistics.clear();
  }
}
