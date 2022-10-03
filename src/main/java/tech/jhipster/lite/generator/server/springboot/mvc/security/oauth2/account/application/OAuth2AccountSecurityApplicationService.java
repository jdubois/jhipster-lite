package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.account.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.account.domain.OAuth2AccountModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class OAuth2AccountSecurityApplicationService {

  private final OAuth2AccountModuleFactory accountsFactory;

  public OAuth2AccountSecurityApplicationService() {
    accountsFactory = new OAuth2AccountModuleFactory();
  }

  public JHipsterModule buildOAuth2AccountModule(JHipsterModuleProperties properties) {
    return accountsFactory.buildModule(properties);
  }
}
