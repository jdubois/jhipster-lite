package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringdocModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdoc/src");
  private static final String DESTINATION = "technical/infrastructure/primary/springdoc";

  private static final String SPRINGDOC_CONFIG_JAVA_FILE = "SpringdocConfiguration.java";
  private static final String SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE = "SpringdocConfigurationSecurityJWT.java";

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

  public JHipsterModule buildModuleForMvc(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_UI_DEPENDENCY, SPRINGDOC_CONFIG_JAVA_FILE);
  }

  public JHipsterModule buildModuleForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY, SPRINGDOC_CONFIG_JAVA_FILE);
  }

  public JHipsterModule buildModuleWithSecurityJwtForMvc(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_UI_DEPENDENCY, SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE);
  }

  public JHipsterModule buildModuleWithSecurityJwtForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY, SPRINGDOC_CONFIG_SECURITY_JWT_JAVA_FILE);
  }

  private JHipsterModule buildModule(
    JHipsterModuleProperties properties,
    JavaDependency springdocJavaDependency,
    String srcSpringdocJavaFile
  ) {
    Assert.notNull("properties", properties);

    JHipsterDestination mainDestination = toSrcMainJava().append(properties.basePackage().path()).append(DESTINATION);

    //@formatter:off
    return moduleBuilder(properties)
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
        .and()
      .build();
    //@formatter:on
  }
}
