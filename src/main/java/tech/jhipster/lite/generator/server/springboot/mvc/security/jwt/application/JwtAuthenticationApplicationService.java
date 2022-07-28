package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtAuthenticationModuleFactory;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain.JwtBasicAuthModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class JwtAuthenticationApplicationService {

  private final JwtAuthenticationModuleFactory authenticationFactory;
  private final JwtBasicAuthModuleFactory basicAuthFactory;

  public JwtAuthenticationApplicationService() {
    authenticationFactory = new JwtAuthenticationModuleFactory();
    basicAuthFactory = new JwtBasicAuthModuleFactory();
  }

  public JHipsterModule buildAuthenticationModule(JHipsterModuleProperties properties) {
    return authenticationFactory.buildModule(properties);
  }

  public JHipsterModule buildBasicAuthModule(JHipsterModuleProperties properties) {
    return basicAuthFactory.buildModule(properties);
  }
}
