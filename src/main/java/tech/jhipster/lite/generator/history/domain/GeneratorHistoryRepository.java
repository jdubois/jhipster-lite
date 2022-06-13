package tech.jhipster.lite.generator.history.domain;

import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

public interface GeneratorHistoryRepository {
  GeneratorHistoryData getHistoryData(JHipsterProjectFolder folder);

  void addHistoryValue(HistoryProject project, GeneratorHistoryValue generatorHistoryValue);
}
