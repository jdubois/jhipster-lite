package tech.jhipster.lite.generator.server.springboot.mvc.web.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcsModulesFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class SpringBootMvcApplicationService {

  private final SpringBootMvcsModulesFactory factory;

  public SpringBootMvcApplicationService() {
    factory = new SpringBootMvcsModulesFactory();
  }

  public JHipsterModule buildTomcatModule(JHipsterModuleProperties properties) {
    return factory.buildTomcatModule(properties);
  }

  public JHipsterModule buildUndertowModule(JHipsterModuleProperties properties) {
    return factory.buildUntertowModule(properties);
  }
}
