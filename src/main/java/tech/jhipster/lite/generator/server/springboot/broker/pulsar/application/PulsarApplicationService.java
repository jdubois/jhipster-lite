package tech.jhipster.lite.generator.server.springboot.broker.pulsar.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain.PulsarService;

@Service
public class PulsarApplicationService {

  private final PulsarService pulsarService;

  public PulsarApplicationService(final PulsarService pulsarService) {
    this.pulsarService = pulsarService;
  }

  public void init(final Project project) {
    pulsarService.init(project);
  }
}
