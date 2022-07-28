package tech.jhipster.lite.generator.server.springboot.logging.logstash.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.domain.LogstashModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class LogstashApplicationService {

  private final LogstashModuleFactory factory;

  public LogstashApplicationService() {
    factory = new LogstashModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
