package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.module.domain.JHipsterDestination;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;
import tech.jhipster.lite.generator.module.domain.javadependency.GroupId;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.replacement.TextMatcher;

public class OAuth2ModuleFactory {

  private static final String TARGET_ANNOTATION = "@Target(ElementType.TYPE)";
  private static final String SPRING_BOOT_IMPORT = "import org.springframework.boot.test.context.SpringBootTest;";
  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");
  private static final String BREAK = "\n";
  private static final String DOMAIN = "domain";
  private static final String PRIMARY = "infrastructure/primary";

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/security/oauth2");
  private static final JHipsterSource MAIN_SOURCE = SOURCE.append("main");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");
  private static final JHipsterSource DOCKER_SOURCE = SOURCE.append("docker");
  private static final JHipsterDestination DOCKER_DESTINATION = to("src/main/docker");

  private final DockerImages dockerImages;

  public OAuth2ModuleFactory(DockerImages dockerImages) {
    Assert.notNull("dockerImages", dockerImages);

    this.dockerImages = dockerImages;
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    JHipsterModuleBuilder builder = moduleBuilder(properties)
      .context()
        .packageName(properties.basePackage())
        .put("applicationName", properties.projectBaseName()
            .capitalized())
        .and();
    //@formatter:on

    appendKeycloak(builder);
    appendJavaFiles(builder, properties);
    appendDependencies(builder);
    appendSpringProperties(builder);
    appendIntegrationTestAnnotationUpdates(builder, properties);

    return builder.build();
  }

  private void appendKeycloak(JHipsterModuleBuilder builder) {
    DockerImage keycloakImage = dockerImages.get("jboss/keycloak");

    builder.context().put("dockerKeycloakVersion", keycloakImage.version()).put("dockerKeycloakImage", keycloakImage.fullName());

    builder
      .files()
      .add(DOCKER_SOURCE.template("keycloak.yml"), DOCKER_DESTINATION.append("keycloak.yml"))
      .batch(DOCKER_SOURCE, DOCKER_DESTINATION.append("keycloak-realm-config"))
      .add("jhipster-realm.json")
      .add("jhipster-users-0.json");
  }

  private void appendJavaFiles(JHipsterModuleBuilder builder, JHipsterModuleProperties properties) {
    String packagePath = properties.basePackage().path();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append("authentication");
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append("authentication");

    //@formatter:off
    builder
    .files()
      .add(MAIN_SOURCE.template("package-info.java"), mainDestination.append("package-info.java"))
      .batch(MAIN_SOURCE.append(DOMAIN), mainDestination.append(DOMAIN))
        .add("Role.java")
        .add("Roles.java")
        .add("Username.java")
        .and()
      .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
        .add("ApplicationSecurityProperties.java")
        .add("AudienceValidator.java")
        .add("AuthenticatedUser.java")
        .add("AuthenticationException.java")
        .add("AuthenticationExceptionAdvice.java")
        .add("Claims.java")
        .add("CustomClaimConverter.java")
        .add("JwtGrantedAuthorityConverter.java")
        .add("NotAuthenticatedUserException.java")
        .add("OAuth2Configuration.java")
        .add("SecurityConfiguration.java")
        .add("UnknownAuthenticationException.java")
        .and()
      .batch(TEST_SOURCE.append(DOMAIN), testDestination.append(DOMAIN))
        .add("RolesTest.java")
        .add("RoleTest.java")
        .add("UsernameTest.java")
        .and()
      .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
        .add("AccountExceptionResource.java")
        .add("ApplicationSecurityPropertiesTest.java")
        .add("AudienceValidatorTest.java")
        .add("AuthenticatedUserTest.java")
        .add("AuthenticationExceptionAdviceIT.java")
        .add("ClaimsTest.java")
        .add("CustomClaimConverterIT.java")
        .add("FakeRequestAttributes.java")
        .add("JwtGrantedAuthorityConverterTest.java")
        .add("SecurityConfigurationIT.java")
        .add("SecurityConfigurationTest.java")
        .add("TestSecurityConfiguration.java")
        .add("WithUnauthenticatedMockUser.java");
    //@formatter:on
  }

  private void appendDependencies(JHipsterModuleBuilder builder) {
    builder
      .javaDependencies()
      .add(SPRING_GROUP, artifactId("spring-boot-starter-security"))
      .add(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-client"))
      .add(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-resource-server"))
      .add(springSecurityTest());
  }

  private void appendSpringProperties(JHipsterModuleBuilder builder) {
    builder
      .springMainProperties()
      .set(
        propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"),
        propertyValue("http://localhost:9080/auth/realms/jhipster")
      )
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-id"), propertyValue("web_app"))
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.client-secret"), propertyValue("web_app"))
      .set(propertyKey("spring.security.oauth2.client.registration.oidc.scope"), propertyValue("openid,profile,email"))
      .set(propertyKey("application.security.oauth2.audience"), propertyValue("account,api://default"));

    builder
      .springTestProperties()
      .set(propertyKey("spring.main.allow-bean-definition-overriding"), propertyValue("true"))
      .set(
        propertyKey("spring.security.oauth2.client.provider.oidc.issuer-uri"),
        propertyValue("http://DO_NOT_CALL:9080/auth/realms/jhipster")
      );
  }

  private void appendIntegrationTestAnnotationUpdates(JHipsterModuleBuilder builder, JHipsterModuleProperties properties) {
    String baseClass = properties.projectBaseName().capitalized() + "App.class";
    TextMatcher importNeedle = text(SPRING_BOOT_IMPORT);

    String integrationtTestFile = "src/test/java/" + properties.basePackage().path() + "/IntegrationTest.java";

    builder
      .mandatoryReplacements()
      .in(integrationtTestFile)
      .add(importNeedle, testSecurityConfigurationImport(properties))
      .add(text(baseClass), baseClass + ", TestSecurityConfiguration.class")
      .add(importNeedle, withMockUserImport())
      .add(text(TARGET_ANNOTATION), TARGET_ANNOTATION + BREAK + "@WithMockUser");
  }

  private String testSecurityConfigurationImport(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append(SPRING_BOOT_IMPORT)
      .append(BREAK)
      .append("import ")
      .append(properties.basePackage().get())
      .append(".authentication.infrastructure.primary.TestSecurityConfiguration;")
      .toString();
  }

  private String withMockUserImport() {
    return SPRING_BOOT_IMPORT + BREAK + "import org.springframework.security.test.context.support.WithMockUser;";
  }

  private JavaDependency springSecurityTest() {
    return JavaDependency
      .builder()
      .groupId("org.springframework.security")
      .artifactId("spring-security-test")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
