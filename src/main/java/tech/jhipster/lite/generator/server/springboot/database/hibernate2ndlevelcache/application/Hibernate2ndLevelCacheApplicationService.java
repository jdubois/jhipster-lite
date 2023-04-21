package tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.hibernate2ndlevelcache.domain.Hibernate2ndLevelCacheModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class Hibernate2ndLevelCacheApplicationService {

  private final Hibernate2ndLevelCacheModuleFactory factory;

  public Hibernate2ndLevelCacheApplicationService() {
    factory = new Hibernate2ndLevelCacheModuleFactory();
  }

  public JHipsterModule build(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
