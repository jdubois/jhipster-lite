package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2AccountModuleFactory;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2SecurityApplicationService {

  private final OAuth2ModuleFactory oAuth2factory;
  private final OAuth2AccountModuleFactory acountsFactory;

  public OAuth2SecurityApplicationService(DockerImages dockerImages) {
    oAuth2factory = new OAuth2ModuleFactory(dockerImages);
    acountsFactory = new OAuth2AccountModuleFactory();
  }

  public JHipsterModule buildOAuth2Module(JHipsterModuleProperties properties) {
    return oAuth2factory.buildModule(properties);
  }

  public JHipsterModule buildOAuth2AccountModule(JHipsterModuleProperties properties) {
    return acountsFactory.buildModule(properties);
  }
}
