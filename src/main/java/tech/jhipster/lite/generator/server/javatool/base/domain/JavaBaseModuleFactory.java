package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterDestination;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterSource;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public class JavaBaseModuleFactory {

  private static final String SOURCE_FOLDER = "server/javatool/base";

  private enum Destination {
    COMMON("common"),
    COMMON_DOMAIN("common/domain"),
    ERROR("error"),
    ERROR_DOMAIN("error/domain");

    private final String path;

    Destination(String path) {
      this.path = path;
    }

    String path() {
      return this.path;
    }
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    String packagePath = properties.basePackage().path();
    String baseClassName = properties.projectBaseName().capitalized();

    JHipsterDestination testDestination = toSrcTestJava().append(packagePath);
    JHipsterDestination mainDestination = toSrcMainJava().append(packagePath);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .packageName(properties.basePackage())
        .put("collectionClass", baseClassName)
        .and()
      .files()
        .batch(source(), mainDestination)
          .add("BusinessContext.java")
          .add("SharedKernel.java")
          .and()
        .batch(source(), mainDestination.append(Destination.ERROR_DOMAIN.path()))
          .add("Assert.java")
          .add("MissingMandatoryValueException.java")
          .add("AssertionException.java")
          .add("NotAfterTimeException.java")
          .add("NotBeforeTimeException.java")
          .add("NullElementInCollectionException.java")
          .add("NumberValueTooHighException.java")
          .add("NumberValueTooLowException.java")
          .add("StringTooLongException.java")
          .add("StringTooShortException.java")
          .add("TooManyElementsException.java")
          .and()
        .batch(source(), testDestination.append(Destination.ERROR_DOMAIN.path()))
          .add("AssertTest.java")
          .add("MissingMandatoryValueExceptionTest.java")
          .and()
        .batch(source(), testDestination)
          .add("UnitTest.java")
          .add("ComponentTest.java")
          .add("ReplaceCamelCase.java")
          .and()
        .add(source().template("package-info-error.java"), packageInfoDestination(mainDestination, Destination.ERROR))
        .add(source().template("package-info-common.java"), packageInfoDestination(mainDestination,  Destination.COMMON))
        .add(source().template("ProjectCollections.java"), collectionsDestination(baseClassName, mainDestination))
        .add(source().template("ProjectCollectionsTest.java"), collectionsTestDestination(baseClassName, testDestination))
        .and()
      .build();
    //@formatter:on
  }

  private JHipsterDestination packageInfoDestination(JHipsterDestination mainDestination, Destination destination) {
    return mainDestination.append(destination.path()).append("package-info.java");
  }

  private JHipsterDestination collectionsDestination(String className, JHipsterDestination mainDestination) {
    return mainDestination.append(Destination.COMMON_DOMAIN.path()).append(className + "Collections.java");
  }

  private JHipsterDestination collectionsTestDestination(String className, JHipsterDestination testDestination) {
    return testDestination.append(Destination.COMMON_DOMAIN.path()).append(className + "CollectionsTest.java");
  }

  private JHipsterSource source() {
    return from(SOURCE_FOLDER);
  }
}
