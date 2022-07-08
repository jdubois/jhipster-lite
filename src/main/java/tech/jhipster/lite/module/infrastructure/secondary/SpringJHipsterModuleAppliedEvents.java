package tech.jhipster.lite.module.infrastructure.secondary;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;

@Component
class SpringJHipsterModuleAppliedEvents implements JHipsterModuleEvents {

  private final ApplicationEventPublisher publisher;

  public SpringJHipsterModuleAppliedEvents(ApplicationEventPublisher publisher) {
    this.publisher = publisher;
  }

  @Override
  public void dispatch(JHipsterModuleApplied moduleApplied) {
    publisher.publishEvent(moduleApplied);
  }
}
