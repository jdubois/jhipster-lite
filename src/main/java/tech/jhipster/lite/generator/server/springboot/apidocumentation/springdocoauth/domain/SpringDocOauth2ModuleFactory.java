package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdocoauth.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringDocOauth2ModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdocoauth");

  public static final JavaDependency SPRINGDOC_OPENAPI_SECURITY_DEPENDENCY = JavaDependency
    .builder()
    .groupId("org.springdoc")
    .artifactId("springdoc-openapi-security")
    .versionSlug("springdoc-openapi.version")
    .build();

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(SPRINGDOC_OPENAPI_SECURITY_DEPENDENCY)
        .and()
      .files()
        .add(
          SOURCE.template("SpringdocOAuth2Configuration.java"),
          toSrcMainJava()
            .append(properties.packagePath())
            .append("technical/infrastructure/primary/springdoc/SpringdocOAuth2Configuration.java")
        )
        .and()
      .springMainProperties()
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), propertyValue("web_app"))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("jhipster"))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/jhipster/protocol/openid-connect/auth")
        )
        .and()
      .springTestProperties()
        .set(propertyKey("springdoc.swagger-ui.oauth.client-id"), propertyValue("web_app"))
        .set(propertyKey("springdoc.swagger-ui.oauth.realm"), propertyValue("jhipster"))
        .set(
          propertyKey("springdoc.oauth2.authorization-url"),
          propertyValue("http://localhost:9080/realms/jhipster/protocol/openid-connect/auth")
        )
        .and()
      .build();
    //@formatter:on
  }
}
