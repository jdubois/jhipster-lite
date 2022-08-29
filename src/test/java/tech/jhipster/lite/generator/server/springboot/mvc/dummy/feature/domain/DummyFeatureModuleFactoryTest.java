package tech.jhipster.lite.generator.server.springboot.mvc.dummy.feature.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class DummyFeatureModuleFactoryTest {

  private static final DummyFeatureModuleFactory factory = new DummyFeatureModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile())
      .createFiles("documentation/dummy.md")
      .createFiles("src/main/java/com/jhipster/test/dummy/application/BeersApplicationService.java")
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/dummy/domain/beer",
        "Beer.java",
        "BeerName.java",
        "Beers.java",
        "BeersCreator.java",
        "BeerSellingState.java",
        "BeersRemover.java",
        "BeersRepository.java",
        "BeerToCreate.java",
        "UnknownBeerException.java"
      )
      .createPrefixedFiles("src/main/java/com/jhipster/test/dummy/domain/order", "BeerOrder.java", "BeerOrderLine.java", "OrderedBeer.java")
      .createPrefixedFiles("src/main/java/com/jhipster/test/dummy/domain", "Amount.java", "BeerId.java")
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/primary/beer",
        "BeersResource.java",
        "RestBeer.java",
        "RestBeers.java",
        "RestBeerToCreate.java"
      )
      .createFiles("src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java")
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test/dummy/domain/beer",
        "BeersFixture.java",
        "BeersRemoverTest.java",
        "BeersTest.java"
      )
      .createPrefixedFiles("src/test/java/com/jhipster/test/dummy/domain/order", "BeerOrderFixture.java", "BeerOrderTest.java")
      .createPrefixedFiles("src/test/java/com/jhipster/test/dummy/domain", "AmountTest.java", "BeersIdentityFixture.java")
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test/dummy/infrastructure/primary/beer",
        "BeersSteps.java",
        "RestBeersTest.java",
        "RestBeerTest.java",
        "RestBeerToCreateTest.java"
      )
      .createFiles("src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java")
      .createFiles("src/test/java/com/jhipster/test/HttpSteps.java")
      .createFiles("src/test/features/beers-catalog.feature");
  }
}
