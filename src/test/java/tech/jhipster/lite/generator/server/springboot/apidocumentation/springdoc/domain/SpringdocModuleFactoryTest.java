package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class SpringdocModuleFactoryTest {

  private static final SpringdocModuleFactory springdocModuleFactory = new SpringdocModuleFactory();

  @Test
  void shouldBuildModuleForMvc() {
    JHipsterModuleProperties moduleProperties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = springdocModuleFactory.buildModuleForMvc(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleForWebflux() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(JHipsterModuleProperties.BASE_PACKAGE_PROPERTY, "com.jhipster.test");
    properties.put(JHipsterModuleProperties.PROJECT_BASE_NAME_PROPERTY, "myapp");
    JHipsterModuleProperties moduleProperties = new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), false, properties);

    JHipsterModule module = springdocModuleFactory.buildModuleForWebflux(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForMvc() {
    JHipsterModuleProperties moduleProperties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForMvc(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();

    assertAddedProperties(moduleAsserter);
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForWebflux() {
    Map<String, Object> properties = new HashMap<>();
    properties.put(JHipsterModuleProperties.BASE_PACKAGE_PROPERTY, "com.jhipster.test");
    properties.put(JHipsterModuleProperties.PROJECT_BASE_NAME_PROPERTY, "myapp");
    JHipsterModuleProperties moduleProperties = new JHipsterModuleProperties(TestFileUtils.tmpDirForTest(), false, properties);

    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForWebflux(moduleProperties);

    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();

    assertAddedProperties(moduleAsserter);
  }

  @Test
  @DisplayName("should build module with Security OAuth2 for MVC")
  void shouldBuildModuleWithSecurityOAuth2ForMvc() {
    JHipsterModuleProperties moduleProperties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("tech.jhipster.free")
      .projectBaseName("freelance")
      .build();

    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityOAuth2ForMvc(moduleProperties);

    // prettier-ignore
    ModuleAsserter moduleAsserter = assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/java/tech/jhipster/free/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
        .containing("OAUTH2")
        .and()
      .createFile("pom.xml")
        .containing("<artifactId>springdoc-openapi-ui</artifactId>")
        .containing("<artifactId>springdoc-openapi-security</artifactId>")
        .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
        .and();

    assertAddedPropertiesWithOAuth2(moduleAsserter);
  }

  private void assertAddedProperties(ModuleAsserter moduleFileAsserter) {
    moduleFileAsserter
      .createFile("src/main/resources/config/application.properties")
      .containing("springdoc.swagger-ui.operationsSorter=alpha")
      .containing("springdoc.swagger-ui.tagsSorter=alpha")
      .containing("springdoc.swagger-ui.tryItOutEnabled=true");
  }

  private void assertAddedPropertiesWithOAuth2(ModuleAsserter moduleFileAsserter) {
    assertAddedProperties(moduleFileAsserter);

    // prettier-ignore
    moduleFileAsserter
      .createFile("src/main/resources/config/application.properties")
        .containing("springdoc.swagger-ui.oauth.client-id=web_app")
        .containing("springdoc.swagger-ui.oauth.realm=jhipster")
        .containing("springdoc.oauth2.authorization-url=http://localhost:9080/auth/realms/jhipster/protocol/openid-connect/auth")
        .and()
      .createFile("src/test/resources/config/application.properties")
        .containing("springdoc.swagger-ui.oauth.client-id=web_app")
        .containing("springdoc.swagger-ui.oauth.realm=jhipster")
        .containing("springdoc.oauth2.authorization-url=http://localhost:9080/auth/realms/jhipster/protocol/openid-connect/auth");
  }
}
