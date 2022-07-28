package tech.jhipster.lite.generator.server.springboot.logging.aop.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class AopLoggingModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/logging/aop");
  private static final String AOP_FOLDER = "technical/infrastructure/secondary/aop/logging";
  private static final PropertyKey AOP_LOG_PROPERTY = propertyKey("application.aop.logging");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.springframework.boot"), artifactId("spring-boot-starter-aop"))
        .and()
      .files()
        .batch(SOURCE, toSrcMainJava().append(packagePath).append(AOP_FOLDER))
          .addTemplate("LoggingAspectConfiguration.java")
          .addTemplate("LoggingAspect.java")
          .and()
        .add(
          SOURCE.template("LoggingAspectTest.java"),
          toSrcTestJava().append(packagePath).append(AOP_FOLDER).append("LoggingAspectTest.java")
        )
        .and()
      .springMainProperties()
        .set(AOP_LOG_PROPERTY, propertyValue("false"))
        .and()
      .springMainProperties(springProfile("local"))
        .set(AOP_LOG_PROPERTY, propertyValue("true"))
        .and()
      .springTestProperties()
        .set(AOP_LOG_PROPERTY, propertyValue("true"))
        .and()
      .build();
    //@formatter:on
  }
}
