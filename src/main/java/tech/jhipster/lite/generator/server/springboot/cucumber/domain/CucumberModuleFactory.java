package tech.jhipster.lite.generator.server.springboot.cucumber.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public class CucumberModuleFactory {

  private static final String CUCUMBER_GROUP_ID = "io.cucumber";
  private static final String CUCUMBER_VERSION = "cucumber.version";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    String applicationName = properties.projectBaseName().capitalized();
    JHipsterSource source = from("server/springboot/cucumber");

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .packageName(properties.basePackage())
        .put("applicationName", applicationName)
        .and()
      .documentation(documentationTitle("Cucumber"), source.template("cucumber.md"))
      .files()
        .batch(source, toSrcTestJava().append(packagePath).append("cucumber"))
          .add("AsyncElementAsserter.java")
          .add("AsyncHeaderAsserter.java")
          .add("AsyncResponseAsserter.java")
          .add("Awaiter.java")
          .add("CucumberAssertions.java")
          .add("CucumberConfiguration.java")
          .add("CucumberJson.java")
          .add("CucumberTest.java")
          .add("CucumberTestContext.java")
          .add("CucumberTestContextUnitTest.java")
          .add("ElementAsserter.java")
          .add("ElementAssertions.java")
          .add("HeaderAsserter.java")
          .add("HeaderAssertions.java")
          .add("ResponseAsserter.java")
          .add("SyncElementAsserter.java")
          .add("SyncHeaderAsserter.java")
          .add("SyncResponseAsserter.java")
          .and()
        .add(source.file("gitkeep"), to("src/test/features/.gitkeep"))
        .and()
      .javaDependencies()
        .add(cucumberJunitDependency())
        .add(cucumberJavaDependency())
        .add(cucumberSpringDependency())
        .add(junitVintageDependency())
        .add(testNgDependency())
        .add(awaitilityDepencency())
        .and()
      .build();
    //@formatter:on
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

  private JavaDependency awaitilityDepencency() {
    return javaDependency().groupId("org.awaitility").artifactId("awaitility").scope(JavaDependencyScope.TEST).build();
  }
}
