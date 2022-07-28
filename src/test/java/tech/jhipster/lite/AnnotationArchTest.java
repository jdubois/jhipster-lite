package tech.jhipster.lite;

import static com.tngtech.archunit.base.DescribedPredicate.*;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@UnitTest
class AnnotationArchTest {

  @Test
  void shouldHaveUnitTestAnnotation() {
    JavaClasses importedClasses = new ClassFileImporter()
      .importPackages("tech.jhipster.lite")
      .that(are(not(equivalentTo(UnitTest.class)).and(not(equivalentTo(IntegrationTest.class)))));

    ArchRule rule = classes()
      .that()
      .resideInAnyPackage("tech.jhipster.lite..")
      .and()
      .haveSimpleNameEndingWith("Test")
      .should()
      .beAnnotatedWith(UnitTest.class)
      .orShould()
      .beAnnotatedWith(ComponentTest.class)
      .orShould()
      .beAnnotatedWith(Nested.class)
      .orShould()
      .beInterfaces();

    rule.check(importedClasses);
  }

  @Test
  void shouldHaveIntegrationTestAnnotation() {
    JavaClasses importedClasses = new ClassFileImporter().importPackages("tech.jhipster.lite");

    ArchRule rule = classes()
      .that()
      .resideInAnyPackage("tech.jhipster.lite..")
      .and()
      .haveSimpleNameEndingWith("IT")
      .should()
      .beAnnotatedWith(IntegrationTest.class)
      .orShould()
      .beAnnotatedWith(Nested.class);

    rule.check(importedClasses);
  }
}
