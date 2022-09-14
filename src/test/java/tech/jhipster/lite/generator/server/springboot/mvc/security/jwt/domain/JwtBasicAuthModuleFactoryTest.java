package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class JwtBasicAuthModuleFactoryTest {

  private static final JwtBasicAuthModuleFactory factory = new JwtBasicAuthModuleFactory();

  @Test
  void shouldBuildBasicAuthModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("jhipster")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .hasFiles("documentation/jwt-basic-auth.md")
      .hasFile("src/main/resources/config/application.properties")
      .containing("application.security.token-validity=P1D")
      .containing("application.security.remember-me-token-validity=P365D")
      .containing("spring.security.user.name=admin")
      .containing("spring.security.user.password=$2a$12$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O")
      .containing("spring.security.user.roles=ADMIN")
      .containing(
        """
        application.security.content-security-policy=\
        default-src 'self'; frame-src 'self' data:; \
        script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; \
        style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; \
        img-src 'self' data:; \
        font-src 'self' data: https://fonts.gstatic.com;\
        """
      )
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("application.security.token-validity=P1D")
      .containing("application.security.remember-me-token-validity=P365D")
      .containing("spring.security.user.name=admin")
      .containing("spring.security.user.password=$2a$12$cRKS9ZURbdJIaRsKDTDUmOrH4.B.2rokv8rrkrQXr2IR2Hkna484O")
      .containing("spring.security.user.roles=ADMIN")
      .containing(
        """
        application.security.content-security-policy=\
        default-src 'self'; frame-src 'self' data:; \
        script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; \
        style-src 'self' 'unsafe-inline' https://fonts.googleapis.com; \
        img-src 'self' data:; \
        font-src 'self' data: https://fonts.gstatic.com;\
        """
      )
      .and()
      .hasFiles("src/main/java/com/jhipster/test/account/package-info.java")
      .hasFiles("src/main/java/com/jhipster/test/account/application/AccountApplicationService.java")
      .hasPrefixedFiles("src/main/java/com/jhipster/test/account/domain", "AuthenticationQuery.java", "Token.java", "TokensRepository.java")
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/account/infrastructure/primary",
        "AccountResource.java",
        "AuthenticationResource.java",
        "Authenticator.java",
        "RestAccount.java",
        "RestAuthenticationQuery.java",
        "RestToken.java"
      )
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/account/infrastructure/secondary",
        "JwtTokensConfiguration.java",
        "JwtTokensProperties.java",
        "JwtTokensRepository.java"
      )
      .hasFiles("src/test/java/com/jhipster/test/account/domain/TokensFixture.java")
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/account/infrastructure/primary",
        "AccountResourceIT.java",
        "AuthenticationResourceIT.java",
        "RestAccountTest.java",
        "RestAuthenticationQueryTest.java",
        "RestTokenTest.java"
      );
  }
}
