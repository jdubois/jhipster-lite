package tech.jhipster.lite.generator.server.springboot.database.postgresql.domain;

import static org.mockito.Mockito.when;
import static tech.jhipster.lite.generator.module.infrastructure.secondary.JHipsterModulesAssertions.assertThatModuleOnProjectWithDefaultPom;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.docker.domain.DockerImage;
import tech.jhipster.lite.generator.docker.domain.DockerImages;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PostgresqlModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private PostgresqlModuleFactory factory;

  @Test
  void shouldCreateModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(FileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    when(dockerImages.get("postgres")).thenReturn(new DockerImage("postgres", "0.0.0"));

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleOnProjectWithDefaultPom(module)
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/secondary/postgresql",
        "DatabaseConfiguration.java",
        "FixedPostgreSQL10Dialect.java"
      )
      .createFile("documentation/postgresql.md")
      .and()
      .createFile("src/test/java/com/jhipster/test/technical/infrastructure/secondary/postgresql/FixedPostgreSQL10DialectTest.java")
      .and()
      .createFile("pom.xml")
      .containing("<groupId>org.postgresql</groupId>")
      .containing("<artifactId>postgresql</artifactId>")
      .containing("<groupId>com.zaxxer</groupId>")
      .containing("<artifactId>HikariCP</artifactId>")
      .containing("<groupId>org.hibernate</groupId>");
  }
}
