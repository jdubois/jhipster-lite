package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.TextMatcher;

public class OAuth2ModuleFactory {

  private static final String TARGET_ANNOTATION = "@Target(ElementType.TYPE)";
  private static final String SPRING_BOOT_IMPORT = "import org.springframework.boot.test.context.SpringBootTest;";
  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");
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
        .put("applicationName", properties.projectBaseName().capitalized())
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
      .template("jhipster-realm.json")
      .template("jhipster-users-0.json");
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
        .template("Role.java")
        .template("Roles.java")
        .template("Username.java")
        .and()
      .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
        .template("ApplicationSecurityProperties.java")
        .template("AudienceValidator.java")
        .template("AuthenticatedUser.java")
        .template("AuthenticationException.java")
        .template("AuthenticationExceptionAdvice.java")
        .template("Claims.java")
        .template("CustomClaimConverter.java")
        .template("JwtGrantedAuthorityConverter.java")
        .template("NotAuthenticatedUserException.java")
        .template("OAuth2Configuration.java")
        .template("SecurityConfiguration.java")
        .template("UnknownAuthenticationException.java")
        .and()
      .batch(TEST_SOURCE.append(DOMAIN), testDestination.append(DOMAIN))
        .template("RolesTest.java")
        .template("RoleTest.java")
        .template("UsernameTest.java")
        .and()
      .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
        .template("AccountExceptionResource.java")
        .template("ApplicationSecurityPropertiesTest.java")
        .template("AudienceValidatorTest.java")
        .template("AuthenticatedUserTest.java")
        .template("AuthenticationExceptionAdviceIT.java")
        .template("ClaimsTest.java")
        .template("CustomClaimConverterIT.java")
        .template("FakeRequestAttributes.java")
        .template("JwtGrantedAuthorityConverterTest.java")
        .template("SecurityConfigurationIT.java")
        .template("SecurityConfigurationTest.java")
        .template("TestSecurityConfiguration.java")
        .template("WithUnauthenticatedMockUser.java");
    //@formatter:on
  }

  private void appendDependencies(JHipsterModuleBuilder builder) {
    builder
      .javaDependencies()
      .dependency(SPRING_GROUP, artifactId("spring-boot-starter-security"))
      .dependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-client"))
      .dependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-resource-server"))
      .dependency(springSecurityTest());
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
      .add(text(TARGET_ANNOTATION), TARGET_ANNOTATION + LINE_BREAK + "@WithMockUser");
  }

  private String testSecurityConfigurationImport(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append(SPRING_BOOT_IMPORT)
      .append(LINE_BREAK)
      .append("import ")
      .append(properties.basePackage().get())
      .append(".authentication.infrastructure.primary.TestSecurityConfiguration;")
      .toString();
  }

  private String withMockUserImport() {
    return SPRING_BOOT_IMPORT + LINE_BREAK + "import org.springframework.security.test.context.support.WithMockUser;";
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
