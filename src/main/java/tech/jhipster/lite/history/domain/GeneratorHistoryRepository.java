package tech.jhipster.lite.history.domain;

import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public interface GeneratorHistoryRepository {
  GeneratorHistoryData getHistoryData(JHipsterProjectFolder folder);

  void addHistoryValue(HistoryProject project, GeneratorHistoryValue generatorHistoryValue);
}
