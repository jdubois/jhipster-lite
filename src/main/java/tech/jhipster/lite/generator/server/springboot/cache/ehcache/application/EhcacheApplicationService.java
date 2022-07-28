package tech.jhipster.lite.generator.server.springboot.cache.ehcache.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.cache.ehcache.domain.EHCacheModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class EhcacheApplicationService {

  private final EHCacheModulesFactory factory;

  public EhcacheApplicationService() {
    factory = new EHCacheModulesFactory();
  }

  public JHipsterModule buildJavaConfigurationModule(JHipsterModuleProperties properties) {
    return factory.buildJavaConfigurationModule(properties);
  }

  public JHipsterModule buildXmlConfigurationModule(JHipsterModuleProperties properties) {
    return factory.buildXmlConfigurationModule(properties);
  }
}
