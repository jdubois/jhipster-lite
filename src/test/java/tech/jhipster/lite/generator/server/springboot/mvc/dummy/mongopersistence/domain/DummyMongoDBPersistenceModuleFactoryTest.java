package tech.jhipster.lite.generator.server.springboot.mvc.dummy.mongopersistence.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class DummyMongoDBPersistenceModuleFactoryTest {

  private static final DummyMongoDBPersistenceModuleFactory factory = new DummyMongoDBPersistenceModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, dummyInMemoryRepository(), inMemoryBeersReseter())
      .hasPrefixedFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerDocument.java",
        "MongoDBBeersRepository.java",
        "SpringDataBeersRepository.java",
        "BeersCollectionChangeUnit.java"
      )
      .hasPrefixedFiles(
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary",
        "BeerDocumentTest.java",
        "MongoDBBeersRepositoryIntTest.java",
        "MongoDBBeersReseter.java"
      )
      .doNotHaveFiles(
        "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java",
        "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
      );
  }

  private ModuleFile dummyInMemoryRepository() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersRepository.java",
      "src/main/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersRepository.java"
    );
  }

  private ModuleFile inMemoryBeersReseter() {
    return file(
      "src/test/resources/projects/dummy-feature/InMemoryBeersReseter.java",
      "src/test/java/com/jhipster/test/dummy/infrastructure/secondary/InMemoryBeersReseter.java"
    );
  }
}
