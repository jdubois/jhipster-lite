package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringdocModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdoc/src");
  private static final String DESTINATION = "technical/infrastructure/primary/springdoc";

  private static final String SPRINGDOC_CONFIG_JAVA_FILE = "SpringdocConfiguration.java";
  private static final String SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE = "SpringdocConfigurationSecurityJWT.java";
  private static final String SPRINGDOC_CONFIG_SECURITY_OAUTH2_JAVA_FILE = "SpringdocConfigurationSecurityOAuth2.java";

  private static final String SPRINGDOC_GROUP_ID = "org.springdoc";
  private static final String SPRINGDOC_OPENAPI_VERSION_KEY = "springdoc-openapi.version";

  public static final JavaDependency SPRINGDOC_OPENAPI_UI_DEPENDENCY = JavaDependency
    .builder()
    .groupId(SPRINGDOC_GROUP_ID)
    .artifactId("springdoc-openapi-ui")
    .versionSlug(SPRINGDOC_OPENAPI_VERSION_KEY)
    .build();

  public static final JavaDependency SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY = JavaDependency
    .builder()
    .groupId(SPRINGDOC_GROUP_ID)
    .artifactId("springdoc-openapi-webflux-ui")
    .versionSlug(SPRINGDOC_OPENAPI_VERSION_KEY)
    .build();

  public static final JavaDependency SPRINGDOC_OPENAPI_SECURITY_DEPENDENCY = JavaDependency
    .builder()
    .groupId(SPRINGDOC_GROUP_ID)
    .artifactId("springdoc-openapi-security")
    .versionSlug(SPRINGDOC_OPENAPI_VERSION_KEY)
    .build();

  public JHipsterModule buildModuleForMvc(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_UI_DEPENDENCY, SPRINGDOC_CONFIG_JAVA_FILE).build();
  }

  public JHipsterModule buildModuleForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY, SPRINGDOC_CONFIG_JAVA_FILE).build();
  }

  public JHipsterModule buildModuleWithSecurityJwtForMvc(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_UI_DEPENDENCY, SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE).build();
  }

  public JHipsterModule buildModuleWithSecurityJwtForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY, SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE).build();
  }

  public JHipsterModule buildModuleWithSecurityOAuth2ForMvc(JHipsterModuleProperties properties) {
    //@formatter:off
    return buildModule(properties, SPRINGDOC_OPENAPI_UI_DEPENDENCY, SPRINGDOC_CONFIG_SECURITY_OAUTH2_JAVA_FILE)
      .javaDependencies()
        .addDependency(SPRINGDOC_OPENAPI_SECURITY_DEPENDENCY)
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

  private JHipsterModuleBuilder buildModule(
    JHipsterModuleProperties properties,
    JavaDependency springdocJavaDependency,
    String srcSpringdocJavaFile
  ) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.packagePath()).append(DESTINATION);

    //@formatter:off
    return moduleBuilder(properties)
      .localEnvironment(localEnvironment("- [Local API doc](http://localhost:" + properties.serverPort().get() + "/swagger-ui/index.html)"))
      .context()
        .put("baseNameLowercase", properties.projectBaseName().uncapitalized())
        .put("apiTitle", "Project API")
        .put("apiDescription", "Project description API")
        .put("apiLicenseName", "No license")
        .put("apiLicenseUrl", "")
        .put("apiExternalDocDescription", "Project Documentation")
        .put("apiExternalDocUrl", "")
        .and()
      .javaDependencies()
        .addDependency(springdocJavaDependency)
        .and()
      .files()
        .add(SOURCE.template(srcSpringdocJavaFile), mainDestination.append(SPRINGDOC_CONFIG_JAVA_FILE))
        .and()
      .springMainProperties()
        .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"))
        .set(propertyKey("springdoc.swagger-ui.tagsSorter"), propertyValue("alpha"))
        .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("true"))
        .and();
    //@formatter:on
  }
}
