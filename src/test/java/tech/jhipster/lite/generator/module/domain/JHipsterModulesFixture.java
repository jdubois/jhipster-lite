package tech.jhipster.lite.generator.module.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.generator.module.domain.properties.JHipsterModulePropertyDefinition.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.ArtifactId;
import tech.jhipster.lite.generator.module.domain.javadependency.CurrentJavaDependenciesVersions;
import tech.jhipster.lite.generator.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.generator.module.domain.javadependency.GroupId;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency.JavaDependencyOptionalValueBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

public final class JHipsterModulesFixture {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModulesFixture.class);

  private JHipsterModulesFixture() {}

  public static JHipsterModule module() {
    // @formatter:off
   return moduleForProject(testModuleProperties())
    .context()
      .put("packageName", "com.test.myapp")
      .and()
    .files()
      .add(from("init/gitignore"), to(".gitignore"))
      .batch(from("server/javatool/base"), to("src/main/java/com/company/myapp/errors"))
        .add("Assert.java.mustache")
        .add("AssertionException.java.mustache")
        .and()
        .add(from("server/springboot/core/MainApp.java.mustache"), to("src/main/java/com/company/myapp/MyApp.java"))
      .add(from("init/README.md.mustache"), to("README.md"))
      .and()
    .documentation(documentationTitle("Cucumber integration"), from("server/springboot/cucumber/cucumber.md.mustache"))
    .mandatoryReplacements()
      .in("src/main/java/com/company/myapp/errors/Assert.java")
        .add(text("Ensure that the input is not null"), "Dummy replacement")
        .add(regex("will be displayed in [^ ]+ message"), "Another dummy replacement")
        .and()
      .and()
    .optionalReplacements()
      .in("src/main/java/com/company/myapp/errors/Assert.java")
        .add(text("Ensure that the given collection is not empty"), "Dummy collection replacement")
        .add(regex("if the collection is [^ ]+ or empty"), "Another dummy collection replacement")
        .and()
      .in("dummy")
        .add(text("Ensure that the input is not null"), "Dummy replacement")
        .and()
      .and()
    .javaDependencies()
      .add(groupId("org.springframework.boot"), artifactId("spring-boot-starter"))
      .add(groupId("org.zalando"), artifactId("problem-spring-web"), versionSlug("problem-spring"))
      .add(optionalTestDependency())
      .add(groupId("io.jsonwebtoken"), artifactId("jjwt-api"), versionSlug("jjwt.version"))
      .and()
    .preActions()
      .add(() -> log.debug("Applying fixture module"))
      .add(() -> log.debug("You shouldn't add this by default in your modules :D"))
      .and()
    .postActions()
      .add(() -> log.debug("Fixture module applied"))
      .add(context -> log.debug("Applied on {}", context.projectFolder().get()))
      .and()
    .springMainProperties()
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("alpha"))
      .and()
    .springMainProperties(springProfile("local"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("false"))
      .and()
    .springTestProperties()
      .set(propertyKey("springdoc.swagger-ui.operationsSorter"), propertyValue("test"))
      .and()
    .springTestProperties(springProfile("local"))
      .set(propertyKey("springdoc.swagger-ui.tryItOutEnabled"), propertyValue("test"))
      .and()
    .build();
    // @formatter:on
  }

  public static JavaDependency defaultVersionDependency() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter").build();
  }

  public static JavaDependency dependencyWithVersion() {
    return javaDependency().groupId("org.zalando").artifactId("problem-spring-web").versionSlug("problem-spring").build();
  }

  public static JavaDependenciesCommands javaDependenciesCommands() {
    SetJavaDependencyVersion setVersion = new SetJavaDependencyVersion(springBootVersion());
    RemoveJavaDependency remove = new RemoveJavaDependency(new DependencyId(new GroupId("spring-boot"), new ArtifactId("1.2.3")));
    AddJavaDependency add = new AddJavaDependency(optionalTestDependency());

    return new JavaDependenciesCommands(List.of(setVersion, remove, add));
  }

  public static JavaDependency optionalTestDependency() {
    return optionalTestDependencyBuilder().build();
  }

  public static JavaDependencyOptionalValueBuilder optionalTestDependencyBuilder() {
    return javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-engine")
      .versionSlug("spring-boot")
      .scope(JavaDependencyScope.TEST)
      .optional();
  }

  public static DependencyId jsonWebTokenDependencyId() {
    return new DependencyId(new GroupId("io.jsonwebtoken"), new ArtifactId("jjwt-api"));
  }

  public static JHipsterModuleContext context() {
    return JHipsterModuleContext.builder(emptyModuleBuilder()).put("packageName", "com.test.myapp").build();
  }

  public static JHipsterModuleBuilder emptyModuleBuilder() {
    return moduleForProject(testModuleProperties());
  }

  public static JHipsterModuleProperties testModuleProperties() {
    return new JHipsterModuleProperties(new JHipsterProjectFolder(FileUtils.tmpDirForTest()), null);
  }

  public static CurrentJavaDependenciesVersions currentJavaDependenciesVersion() {
    return new CurrentJavaDependenciesVersions(List.of(springBootVersion(), problemVersion()));
  }

  private static JavaDependencyVersion problemVersion() {
    return new JavaDependencyVersion("problem-spring", "3.4.5");
  }

  public static JavaDependencyVersion springBootVersion() {
    return new JavaDependencyVersion("spring-boot", "1.2.3");
  }

  public static JHipsterModuleProperties allProperties() {
    return new JHipsterModuleProperties(
      new JHipsterProjectFolder("/test"),
      Map.of(
        "packageName",
        "tech.jhipster.chips",
        "prettierDefaultIndent",
        2,
        "projectName",
        "JHipster project",
        "baseName",
        "jhipster",
        "optionalString",
        "optional",
        "mandatoryInteger",
        42,
        "mandatoryBoolean",
        true,
        "optionalBoolean",
        true
      )
    );
  }

  public static JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition
      .builder()
      .addBasePackage()
      .addIndentation()
      .addProjectName()
      .addProjectBaseName()
      .add(optionalStringProperty("optionalString").build())
      .add(mandatoryIntegerProperty("mandatoryInteger").build())
      .add(mandatoryBooleanProperty("mandatoryBoolean").build())
      .add(optionalBooleanProperty("optionalBoolean").build())
      .build();
  }

  public static JHipsterModulePropertiesBuilder propertiesBuilder(String projectFolder) {
    return new JHipsterModulePropertiesBuilder(projectFolder);
  }

  public static SpringProperty springLocalMainProperty() {
    return SpringProperty
      .mainPropertyBuilder()
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .profile(springProfile("local"))
      .build();
  }

  public static SpringProperty springMainProperty() {
    return SpringProperty
      .mainPropertyBuilder()
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static SpringProperty springTestProperty() {
    return SpringProperty
      .testPropertyBuilder()
      .key(propertyKey("springdoc.swagger-ui.operationsSorter"))
      .value(propertyValue("alpha", "beta"))
      .build();
  }

  public static class JHipsterModulePropertiesBuilder {

    private final String projectFolder;
    private final Map<String, Object> properties = new HashMap<>();

    private JHipsterModulePropertiesBuilder(String projectFolder) {
      this.projectFolder = projectFolder;
    }

    public JHipsterModulePropertiesBuilder basePackage(String basePackage) {
      properties.put("packageName", basePackage);

      return this;
    }

    public JHipsterModulePropertiesBuilder projectBaseName(String projectBaseName) {
      properties.put("baseName", projectBaseName);

      return this;
    }

    public JHipsterModuleProperties build() {
      return new JHipsterModuleProperties(projectFolder, properties);
    }
  }
}
