package tech.jhipster.lite.generator.server.springboot.beanvalidationtest.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class BeanValidationTestModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/beanvalidationtest");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(reflectionsDependency())
        .and()
      .files()
        .batch(SOURCE, toSrcTestJava().append(properties.packagePath()))
          .addTemplate("BeanValidationAssertions.java")
          .addTemplate("BeanValidationTest.java")
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private JavaDependency reflectionsDependency() {
    return javaDependency()
      .groupId("org.reflections")
      .artifactId("reflections")
      .versionSlug("reflections")
      .scope(JavaDependencyScope.TEST)
      .build();
  }
}
