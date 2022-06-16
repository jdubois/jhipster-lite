package tech.jhipster.lite.generator.server.springboot.mvc.security.oauth2.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.generator.module.domain.JHipsterDestination;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public class OAuth2AccountModuleFactory {

  private static final String PACKAGE_INFO = "package-info.java";
  private static final String APPLICATION = "application";
  private static final String DOMAIN = "domain";
  private static final String INFRASTRUCTURE = "infrastructure";
  private static final String PRIMARY = INFRASTRUCTURE + "/primary";
  private static final String SECONDARY = INFRASTRUCTURE + "/secondary";

  private static final JHipsterSource ACCOUNT_SOURCE = from("server/springboot/mvc/security/oauth2/account");
  private static final JHipsterSource ACCOUNT_MAIN_SOURCE = ACCOUNT_SOURCE.append("main");
  private static final JHipsterSource ACCOUNT_TEST_SOURCE = ACCOUNT_SOURCE.append("test");

  private static final JHipsterSource USER_IDENTITY_SOURCE = from("server/springboot/mvc/security/oauth2/useridentity");
  private static final JHipsterSource USER_IDENTITY_MAIN_SOURCE = USER_IDENTITY_SOURCE.append("main");
  private static final JHipsterSource USER_IDENTITY_TEST_SOURCE = USER_IDENTITY_SOURCE.append("test");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    String packagePath = properties.basePackage().path();
    JHipsterDestination accountMainDestination = toSrcMainJava().append(packagePath).append("account");
    JHipsterDestination accountTestDestination = toSrcTestJava().append(packagePath).append("account");

    JHipsterDestination userIdentityMainDestination = toSrcMainJava().append(packagePath).append("useridentity");
    JHipsterDestination userIdentityTestDestination = toSrcTestJava().append(packagePath).append("useridentity");

    //@formatter:off
    return moduleBuilder(properties)
        .context()
          .packageName(properties.basePackage())
          .and()
        .files()
          .add(ACCOUNT_MAIN_SOURCE.append(APPLICATION).template("AccountsApplicationService.java"), accountMainDestination.append(APPLICATION).append("AccountsApplicationService.java"))
          .batch(ACCOUNT_MAIN_SOURCE.append(DOMAIN), accountMainDestination.append(DOMAIN))
            .add("Account.java")
            .add("AccountsRepository.java")
            .and()
          .batch(ACCOUNT_MAIN_SOURCE.append(PRIMARY), accountMainDestination.append(PRIMARY))
            .add("RestAccount.java")
            .add("AccountsResource.java")
            .and()
          .batch(ACCOUNT_MAIN_SOURCE.append(SECONDARY), accountMainDestination.append(SECONDARY))
            .add("OAuth2AccountsRepository.java")
            .add("OAuth2AuthenticationReader.java")
            .add("UnknownAuthenticationSchemeException.java")
            .and()
          .add(ACCOUNT_MAIN_SOURCE.template(PACKAGE_INFO), accountMainDestination.append(PACKAGE_INFO))
          .add(ACCOUNT_TEST_SOURCE.append(DOMAIN).template("AccountsFixture.java"), accountTestDestination.append(DOMAIN).append("AccountsFixture.java"))
          .batch(ACCOUNT_TEST_SOURCE.append(PRIMARY), accountTestDestination.append(PRIMARY))
            .add("RestAccountTest.java")
            .add("AccountsResourceIntTest.java")
            .add("AccountsResourceTest.java")
            .and()
          .add(ACCOUNT_TEST_SOURCE.append(SECONDARY).template("OAuth2AuthenticationReaderTest.java"), accountTestDestination.append(SECONDARY).append("OAuth2AuthenticationReaderTest.java"))
          .add(ACCOUNT_TEST_SOURCE.append(INFRASTRUCTURE).template("OAuth2TokenFixture.java"), accountTestDestination.append(INFRASTRUCTURE).append("OAuth2TokenFixture.java"))
          .batch(USER_IDENTITY_MAIN_SOURCE.append(DOMAIN), userIdentityMainDestination.append(DOMAIN))
            .add("Email.java")
            .add("Firstname.java")
            .add("Lastname.java")
            .add("Name.java")
            .and()
          .add(USER_IDENTITY_MAIN_SOURCE.template(PACKAGE_INFO), userIdentityMainDestination.append(PACKAGE_INFO))
          .batch(USER_IDENTITY_TEST_SOURCE.append(DOMAIN), userIdentityTestDestination.append(DOMAIN))
            .add("EmailTest.java")
            .add("FirstnameTest.java")
            .add("LastnameTest.java")
            .add("NameTest.java")
            .add("UsersIdentitiesFixture.java")
            .and()
          .and()
        .build();
    //@formatter:on
  }
}
