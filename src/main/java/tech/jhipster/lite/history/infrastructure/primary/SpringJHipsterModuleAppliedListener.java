package tech.jhipster.lite.history.infrastructure.primary;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.history.domain.HistoryProject;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;

@Component
class SpringJHipsterModuleAppliedListener implements ApplicationListener<PayloadApplicationEvent<JHipsterModuleApplied>> {

  private final GeneratorHistoryApplicationService history;

  public SpringJHipsterModuleAppliedListener(GeneratorHistoryApplicationService history) {
    this.history = history;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<JHipsterModuleApplied> event) {
    JHipsterModuleApplied payload = event.getPayload();

    history.addHistoryValue(HistoryProject.from(payload.properties()), new GeneratorHistoryValue(payload.slug().get(), payload.time()));
  }
}
