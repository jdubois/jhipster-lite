package tech.jhipster.lite.generator.buildtool.maven.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class MavenApplicationService {

  private final MavenModuleFactory factory;

  public MavenApplicationService() {
    factory = new MavenModuleFactory();
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
