package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.generator.server.springboot.mvc.security.common.domain.AuthenticationModulesFactory.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;

public class OAuth2ModuleFactory {

  private static final TextNeedleBeforeReplacer IMPORT_NEEDLE = lineBeforeText(
    "import org.springframework.boot.test.context.SpringBootTest;"
  );
  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");
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

    JHipsterModuleBuilder builder = authenticationModuleBuilder(properties);

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
      .addTemplate("jhipster-realm.json")
      .addTemplate("jhipster-users-0.json");
  }

  private void appendJavaFiles(JHipsterModuleBuilder builder, JHipsterModuleProperties properties) {
    String packagePath = properties.basePackage().path();
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath).append("authentication");
    JHipsterDestination testDestination = toSrcTestJava().append(packagePath).append("authentication");

    //@formatter:off
    builder
    .files()
      .batch(MAIN_SOURCE.append(PRIMARY), mainDestination.append(PRIMARY))
        .addTemplate("ApplicationSecurityProperties.java")
        .addTemplate("AudienceValidator.java")
        .addTemplate("AuthenticatedUser.java")
        .addTemplate("Claims.java")
        .addTemplate("CustomClaimConverter.java")
        .addTemplate("JwtGrantedAuthorityConverter.java")
        .addTemplate("OAuth2Configuration.java")
        .addTemplate("SecurityConfiguration.java")
        .and()
      .batch(TEST_SOURCE.append(PRIMARY), testDestination.append(PRIMARY))
        .addTemplate("ApplicationSecurityPropertiesTest.java")
        .addTemplate("AudienceValidatorTest.java")
        .addTemplate("AuthenticatedUserTest.java")
        
        .addTemplate("ClaimsTest.java")
        .addTemplate("CustomClaimConverterIT.java")
        .addTemplate("FakeRequestAttributes.java")
        .addTemplate("JwtGrantedAuthorityConverterTest.java")
        .addTemplate("SecurityConfigurationIT.java")
        .addTemplate("SecurityConfigurationTest.java")
        .addTemplate("TestSecurityConfiguration.java")
        .addTemplate("WithUnauthenticatedMockUser.java");
    //@formatter:on
  }

  private void appendDependencies(JHipsterModuleBuilder builder) {
    builder
      .javaDependencies()
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-client"))
      .addDependency(SPRING_GROUP, artifactId("spring-boot-starter-oauth2-resource-server"));
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

    String integrationTestFile = "src/test/java/" + properties.packagePath() + "/IntegrationTest.java";

    builder
      .mandatoryReplacements()
      .in(integrationTestFile)
      .add(IMPORT_NEEDLE, testSecurityConfigurationImport(properties))
      .add(text(baseClass), baseClass + ", TestSecurityConfiguration.class");
  }

  private String testSecurityConfigurationImport(JHipsterModuleProperties properties) {
    return new StringBuilder()
      .append("import ")
      .append(properties.basePackage().get())
      .append(".authentication.infrastructure.primary.TestSecurityConfiguration;")
      .toString();
  }
}
