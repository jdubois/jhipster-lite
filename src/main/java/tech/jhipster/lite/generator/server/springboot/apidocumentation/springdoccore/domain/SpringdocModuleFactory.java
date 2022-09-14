package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoccore.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class SpringdocModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/apidocumentation/springdoccore");
  private static final String DESTINATION = "technical/infrastructure/primary/springdoc";

  private static final String SPRINGDOC_CONFIG_JAVA_FILE = "SpringdocConfiguration.java";

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
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_UI_DEPENDENCY).build();
  }

  public JHipsterModule buildModuleForWebflux(JHipsterModuleProperties moduleProperties) {
    return buildModule(moduleProperties, SPRINGDOC_OPENAPI_WEBFLUX_UI_DEPENDENCY).build();
  }

  private JHipsterModuleBuilder buildModule(JHipsterModuleProperties properties, JavaDependency springdocJavaDependency) {
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
        .add(SOURCE.template(SPRINGDOC_CONFIG_JAVA_FILE), mainDestination.append(SPRINGDOC_CONFIG_JAVA_FILE))
        .and()
      .springMainProperties()
        .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"))
        .set(propertyKey("springdoc.swagger-ui.tagsSorter"), propertyValue("alpha"))
        .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("true"))
        .and();
    //@formatter:on
  }
}
