package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.propertyValue;
import static tech.jhipster.lite.module.domain.JHipsterModule.toSrcMainJava;

import java.util.Map;
import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleContext;
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

  //@formatter:off
  private static final Map<String, String> DEFAULT_API_CONFIG_MAP = Map.of(
    "apiTitle", "Project API",
    "apiDescription", "Project description API",
    "apiLicenseName", "No license",
    "apiLicenseUrl", "",
    "apiExternalDocDescription", "Project Documentation",
    "apiExternalDocUrl", ""
  );

  //@formatter:on

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
    JHipsterModuleProperties moduleProperties,
    JavaDependency springdocJavaDependency,
    String srcSpringdocJavaFile
  ) {
    Assert.notNull("properties", moduleProperties);

    JHipsterDestination mainDestination = toSrcMainJava().append(moduleProperties.basePackage().path()).append(DESTINATION);

    JHipsterModuleContext.JHipsterModuleContextBuilder moduleContextBuilder = moduleBuilder(moduleProperties).context();

    String baseName = moduleProperties.projectBaseName().get();
    moduleContextBuilder.put("baseNameLowercase", WordUtils.lowerFirst(baseName));
    DEFAULT_API_CONFIG_MAP.forEach(moduleContextBuilder::put);

    //@formatter:off
    return moduleContextBuilder
        .and()
      .javaDependencies()
        .dependency(springdocJavaDependency)
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
