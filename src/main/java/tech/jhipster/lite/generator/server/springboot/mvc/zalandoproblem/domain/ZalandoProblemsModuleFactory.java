package tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class ZalandoProblemsModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/mvc/zalandoproblem");
  private static final JHipsterSource TEST_SOURCE = SOURCE.append("test");

  private static final PropertyKey EXCEPTION_PACKAGE = propertyKey("application.exception.package");
  private static final String EXCEPTION_PATH = "technical/infrastructure/primary/exception";

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.packagePath();

    //@formatter:off
    return moduleBuilder(properties)
      .javaDependencies()
        .addDependency(groupId("org.zalando"), artifactId("problem-spring-web"), versionSlug("problem-spring"))
        .and()
      .springMainProperties()
        .set(propertyKey("application.exception.details"), propertyValue("false"))
        .set(EXCEPTION_PACKAGE, propertyValue("org.", "java.", "net.", "javax.", "com.", "io.", "de.", properties.basePackage().get()))
        .and()
      .springTestProperties()
        .set(EXCEPTION_PACKAGE, propertyValue("org.", "java."))
        .and()
      .files()
        .batch(SOURCE.append("main"), toSrcMainJava().append(packagePath).append(EXCEPTION_PATH))
          .addTemplate("BadRequestAlertException.java")
          .addTemplate("ErrorConstants.java")
          .addTemplate("ExceptionTranslator.java")
          .addTemplate("FieldErrorDTO.java")
          .addTemplate("HeaderUtil.java")
          .addTemplate("ProblemConfiguration.java")
          .and()
        .batch(TEST_SOURCE, toSrcTestJava().append(packagePath).append(EXCEPTION_PATH))
          .addTemplate("BadRequestAlertExceptionTest.java")
          .addTemplate("ExceptionTranslatorIT.java")
          .addTemplate("ExceptionTranslatorTestController.java")
          .addTemplate("FieldErrorDTOTest.java")
          .addTemplate("HeaderUtilTest.java")
          .and()
        .add(TEST_SOURCE.template("TestUtil.java"), toSrcTestJava().append(packagePath).append("TestUtil.java"))
        .and()
      .build();
    //@formatter:on
  }
}
