package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.auth0.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.auth0.domain.OAuth2Auth0ModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2Auth0SecurityApplicationService {

  private final OAuth2Auth0ModuleFactory oAuth2Auth0ModuleFactory;

  public OAuth2Auth0SecurityApplicationService() {
    oAuth2Auth0ModuleFactory = new OAuth2Auth0ModuleFactory();
  }

  public JHipsterModule buildOAuth2Auth0Module(JHipsterModuleProperties properties) {
    return oAuth2Auth0ModuleFactory.buildModule(properties);
  }
}
