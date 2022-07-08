package tech.jhipster.lite.generator.server.javatool.base.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class JavaBaseModuleFactory {

  private static final JHipsterSource SOURCE = from("server/javatool/base");

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
        .put("collectionClass", baseClassName)
        .and()
      .documentation(documentationTitle("Package types"), SOURCE.template("package-types.md"))
      .documentation(documentationTitle("Assertions"), SOURCE.template("assertions.md"))
      .files()
        .batch(SOURCE, mainDestination)
          .template("BusinessContext.java")
          .template("SharedKernel.java")
          .and()
        .batch(SOURCE, mainDestination.append(Destination.ERROR_DOMAIN.path()))
          .template("Assert.java")
          .template("MissingMandatoryValueException.java")
          .template("AssertionException.java")
          .template("NotAfterTimeException.java")
          .template("NotBeforeTimeException.java")
          .template("NullElementInCollectionException.java")
          .template("NumberValueTooHighException.java")
          .template("NumberValueTooLowException.java")
          .template("StringTooLongException.java")
          .template("StringTooShortException.java")
          .template("TooManyElementsException.java")
          .and()
        .batch(SOURCE, testDestination.append(Destination.ERROR_DOMAIN.path()))
          .template("AssertTest.java")
          .template("MissingMandatoryValueExceptionTest.java")
          .and()
        .batch(SOURCE, testDestination)
          .template("UnitTest.java")
          .template("ComponentTest.java")
          .template("ReplaceCamelCase.java")
          .and()
        .add(SOURCE.template("package-info-error.java"), packageInfoDestination(mainDestination, Destination.ERROR))
        .add(SOURCE.template("package-info-common.java"), packageInfoDestination(mainDestination,  Destination.COMMON))
        .add(SOURCE.template("Generated.java"), mainDestination.append(Destination.COMMON_DOMAIN.path).append("Generated.java"))
        .add(SOURCE.template("ProjectCollections.java"), collectionsDestination(baseClassName, mainDestination))
        .add(SOURCE.template("ProjectCollectionsTest.java"), collectionsTestDestination(baseClassName, testDestination))
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
}
