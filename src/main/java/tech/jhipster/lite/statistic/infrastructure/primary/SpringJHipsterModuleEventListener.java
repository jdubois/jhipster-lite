package tech.jhipster.lite.statistic.infrastructure.primary;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.statistic.application.StatisticsApplicationService;
import tech.jhipster.lite.statistic.domain.AppliedModule;
import tech.jhipster.lite.statistic.domain.AppliedModuleId;
import tech.jhipster.lite.statistic.domain.Module;
import tech.jhipster.lite.statistic.domain.ModuleProperties;
import tech.jhipster.lite.statistic.domain.ProjectPath;

@Component
class SpringJHipsterModuleEventListener implements ApplicationListener<PayloadApplicationEvent<JHipsterModuleApplied>> {

  private final StatisticsApplicationService statistics;

  public SpringJHipsterModuleEventListener(StatisticsApplicationService statistics) {
    this.statistics = statistics;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<JHipsterModuleApplied> event) {
    statistics.moduleApplied(toModuleApplied(event.getPayload()));
  }

  private AppliedModule toModuleApplied(JHipsterModuleApplied moduleApplied) {
    return AppliedModule
      .builder()
      .id(AppliedModuleId.newId())
      .path(new ProjectPath(moduleApplied.properties().projectFolder().get()))
      .module(new Module(moduleApplied.slug().get()))
      .date(moduleApplied.time())
      .properties(new ModuleProperties(moduleApplied.properties().getParameters()));
  }
}
