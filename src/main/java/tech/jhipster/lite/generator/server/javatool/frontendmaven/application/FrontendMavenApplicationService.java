package tech.jhipster.lite.generator.server.javatool.frontendmaven.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.javatool.frontendmaven.domain.FrontendMavenModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.npm.NpmVersions;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class FrontendMavenApplicationService {

  private final FrontendMavenModuleFactory factory;

  public FrontendMavenApplicationService(NpmVersions npmVersions) {
    factory = new FrontendMavenModuleFactory(npmVersions);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
