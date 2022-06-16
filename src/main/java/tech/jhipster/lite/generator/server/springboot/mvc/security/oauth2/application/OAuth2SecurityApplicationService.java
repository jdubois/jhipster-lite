package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2AccountModuleFactory;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain.OAuth2ModuleFactory;

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
