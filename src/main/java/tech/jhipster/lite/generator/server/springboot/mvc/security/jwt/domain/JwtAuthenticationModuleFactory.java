package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.AuthenticationModulesFactory.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.base64.domain.Base64Utils;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.LogLevel;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class JwtAuthenticationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/jwt/authentication");

  private static final GroupId JJWT_GROUP = groupId("io.jsonwebtoken");
  private static final VersionSlug JJWT_VERSION = versionSlug("jjwt");

  private static final String AUTHENTICATION_PRIMARY = "authentication/infrastructure/primary";

  private static final PropertyKey BASE_SECRET_64_PROPERTY_KEY = propertyKey("application.security.jwt-base64-secret");

  private static final String SPRING_SECURITY_PACKAGE = "org.springframework.security";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return authenticationModuleBuilder(properties)
      .javaDependencies()
        .addDependency(JJWT_GROUP, artifactId("jjwt-api"), JJWT_VERSION)
        .addDependency(jjwtImplDependency())
        .addDependency(jjwtJacksonDependency())
        .and()
      .files()
        .batch(SOURCE.append("main/infrastructure/primary"), toSrcMainJava().append(packagePath).append(AUTHENTICATION_PRIMARY))
          .addTemplate("AuthenticatedUser.java")
          .addTemplate("AuthenticationTokenReader.java")
          .addTemplate("JwtAuthenticationProperties.java")
          .addTemplate("JWTConfigurer.java")
          .addTemplate("JWTFilter.java")
          .addTemplate("JwtReader.java")
          .addTemplate("SecurityConfiguration.java")
          .addTemplate("SecurityProblemSupport.java")
          .and()
        .batch(SOURCE.append("test/infrastructure/primary"), toSrcTestJava().append(packagePath).append(AUTHENTICATION_PRIMARY))
          .addTemplate("AuthenticatedUserTest.java")
          .addTemplate("JWTFilterTest.java")
          .addTemplate("JwtReaderTest.java")
          .and()
        .and()
      .springMainProperties()
        .set(BASE_SECRET_64_PROPERTY_KEY, propertyValue(Base64Utils.getBase64Secret()))
        .and()
      .springTestProperties()
        .set(BASE_SECRET_64_PROPERTY_KEY, propertyValue(Base64Utils.getBase64Secret()))
        .and()
      .springMainLogger(SPRING_SECURITY_PACKAGE, LogLevel.WARN)
      .springTestLogger(SPRING_SECURITY_PACKAGE, LogLevel.WARN)
      .build();
    //@formatter:on
  }

  private JavaDependency jjwtImplDependency() {
    return javaDependency()
      .groupId(JJWT_GROUP)
      .artifactId("jjwt-impl")
      .versionSlug(JJWT_VERSION)
      .scope(JavaDependencyScope.RUNTIME)
      .build();
  }

  private JavaDependency jjwtJacksonDependency() {
    return javaDependency()
      .groupId(JJWT_GROUP)
      .artifactId("jjwt-jackson")
      .versionSlug(JJWT_VERSION)
      .scope(JavaDependencyScope.RUNTIME)
      .build();
  }
}
