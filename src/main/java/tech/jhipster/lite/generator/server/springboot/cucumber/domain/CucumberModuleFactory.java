package tech.jhipster.lite.generator.server.springboot.cucumber.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class CucumberModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/cucumber");
  private static final String CUCUMBER_GROUP_ID = "io.cucumber";
  private static final String CUCUMBER_VERSION = "cucumber.version";

  public JHipsterModule buildInitializationModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String applicationName = properties.projectBaseName().capitalized();
    JHipsterDestination destination = toSrcTestJava().append(properties.packagePath()).append("cucumber");

    //@formatter:off
    JHipsterModuleBuilder builder = moduleBuilder(properties)
    .context()
      .put("applicationName", applicationName)
      .and()
    .documentation(documentationTitle("Cucumber"), SOURCE.template("cucumber.md"))
    .files()
      .batch(SOURCE, destination)
        .addTemplate("AsyncElementAsserter.java")
        .addTemplate("AsyncHeaderAsserter.java")
        .addTemplate("AsyncResponseAsserter.java")
        .addTemplate("Awaiter.java")
        .addTemplate("CucumberAssertions.java")
        .addTemplate("CucumberRestTemplate.java")
        .addTemplate("CucumberConfiguration.java")
        .addTemplate("CucumberJson.java")
        .addTemplate("CucumberTest.java")
        .addTemplate("CucumberTestContext.java")
        .addTemplate("CucumberTestContextUnitTest.java")
        .addTemplate("ElementAsserter.java")
        .addTemplate("ElementAssertions.java")
        .addTemplate("HeaderAsserter.java")
        .addTemplate("HeaderAssertions.java")
        .addTemplate("ResponseAsserter.java")
        .addTemplate("SyncElementAsserter.java")
        .addTemplate("SyncHeaderAsserter.java")
        .addTemplate("SyncResponseAsserter.java")
        .and()
      .add(SOURCE.file("gitkeep"), to("src/test/features/.gitkeep"))
      .and()
    .javaDependencies()
      .addDependency(cucumberJunitDependency())
      .addDependency(cucumberJavaDependency())
      .addDependency(cucumberSpringDependency())
      .addDependency(junitVintageDependency())
      .addDependency(testNgDependency())
      .addDependency(awaitilityDependency())
      .and();
    //@formatter:on

    return builder.build();
  }

  private JavaDependency cucumberJunitDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-junit")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency cucumberJavaDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-java")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency cucumberSpringDependency() {
    return javaDependency()
      .groupId(CUCUMBER_GROUP_ID)
      .artifactId("cucumber-spring")
      .versionSlug(CUCUMBER_VERSION)
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency junitVintageDependency() {
    return javaDependency().groupId("org.junit.vintage").artifactId("junit-vintage-engine").scope(JavaDependencyScope.TEST).build();
  }

  private JavaDependency testNgDependency() {
    return javaDependency()
      .groupId("org.testng")
      .artifactId("testng")
      .versionSlug("testng.version")
      .scope(JavaDependencyScope.TEST)
      .build();
  }

  private JavaDependency awaitilityDependency() {
    return javaDependency().groupId("org.awaitility").artifactId("awaitility").scope(JavaDependencyScope.TEST).build();
  }

  public JHipsterModule buildJpaResetModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .files()
      .add(
        SOURCE.template("CucumberJpaReset.java"),
        toSrcTestJava().append(properties.packagePath()).append("cucumber").append("CucumberJpaReset.java")
      )
      .and()
      .build();
  }
}
