package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

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
    JHipsterModule module = springdocModuleFactory.buildModuleForMvc(properties());

    assertThatSpringDocModule(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
  }

  @Test
  void shouldBuildModuleForWebflux() {
    JHipsterModule module = springdocModuleFactory.buildModuleForWebflux(properties());

    assertThatSpringDocModule(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .notContaining("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForMvc() {
    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForMvc(properties());

    assertThatSpringDocModule(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-ui</artifactId>")
      .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
  }

  @Test
  void shouldBuildModuleWithSecurityJwtForWebflux() {
    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityJwtForWebflux(properties());

    assertThatSpringDocModule(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
      .containing("JWT")
      .and()
      .createFile("pom.xml")
      .containing("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
      .and();
  }

  @Test
  @DisplayName("should build module with Security OAuth2 for MVC")
  void shouldBuildModuleWithSecurityOAuth2ForMvc() {
    JHipsterModule module = springdocModuleFactory.buildModuleWithSecurityOAuth2ForMvc(properties());

    //@formatter:off
    assertThatSpringDocModule(module)
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/primary/springdoc/SpringdocConfiguration.java")
        .containing("OAUTH2")
        .and()
      .createFile("pom.xml")
        .containing("<artifactId>springdoc-openapi-ui</artifactId>")
        .containing("<artifactId>springdoc-openapi-security</artifactId>")
        .notContaining("<artifactId>springdoc-openapi-webflux-ui</artifactId>")
        .and()
      .createFile("src/main/resources/config/application.properties")
        .containing("springdoc.swagger-ui.oauth.client-id=web_app")
        .containing("springdoc.swagger-ui.oauth.realm=jhipster")
        .containing("springdoc.oauth2.authorization-url=http://localhost:9080/realms/jhipster/protocol/openid-connect/auth")
        .and()
      .createFile("src/test/resources/config/application.properties")
        .containing("springdoc.swagger-ui.oauth.client-id=web_app")
        .containing("springdoc.swagger-ui.oauth.realm=jhipster")
        .containing("springdoc.oauth2.authorization-url=http://localhost:9080/realms/jhipster/protocol/openid-connect/auth");
    ;
    //@formatter:on
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();
  }

  private static ModuleAsserter assertThatSpringDocModule(JHipsterModule module) {
    return assertThatModuleWithFiles(module, pomFile(), readmeFile())
      .createFile("src/main/resources/config/application.properties")
      .containing("springdoc.swagger-ui.operationsSorter=alpha")
      .containing("springdoc.swagger-ui.tagsSorter=alpha")
      .containing("springdoc.swagger-ui.tryItOutEnabled=true")
      .and()
      .createFile("README.md")
      .containing("- [Local API doc](http://localhost:8080/swagger-ui/index.html)")
      .and();
  }
}
