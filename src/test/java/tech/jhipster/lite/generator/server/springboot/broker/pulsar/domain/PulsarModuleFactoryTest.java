package tech.jhipster.lite.generator.server.springboot.broker.pulsar.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
@ExtendWith(MockitoExtension.class)
class PulsarModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private PulsarModuleFactory factory;

  @Test
  void shouldBuildModule() {
    when(dockerImages.get("apachepulsar/pulsar")).thenReturn(new DockerImage("apachepulsar/pulsar", "1.1.1"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), integrationTestAnnotation())
      .createFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>org.apache.pulsar</groupId>
              <artifactId>pulsar-client</artifactId>
              <version>${pulsar.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>org.testcontainers</groupId>
              <artifactId>pulsar</artifactId>
              <version>${testcontainers.version}</version>
              <scope>test</scope>
            </dependency>
        """
      )
      .and()
      .createFile("src/main/docker/pulsar.yml")
      .containing("apachepulsar/pulsar:1.1.1")
      .and()
      .createFile("src/main/resources/config/application.properties")
      .containing("pulsar.client.service-url=pulsar://localhost:6650")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("pulsar.client.num-io-threads=8")
      .containing("pulsar.producer.topic-name=test-topic")
      .containing("pulsar.consumer.topic-names[0]=test-topic")
      .containing("pulsar.consumer.subscription-name=test-subscription")
      .and()
      .createJavaTests("com/jhipster/test/PulsarTestContainerExtension.java")
      .createFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("import org.junit.jupiter.api.extension.ExtendWith;")
      .containing("@ExtendWith(PulsarTestContainerExtension.class)")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test/technical/infrastructure/config/pulsar",
        "PulsarProperties.java",
        "PulsarConfiguration.java"
      )
      .createFiles("src/test/java/com/jhipster/test/technical/infrastructure/config/pulsar/PulsarConfigurationIT.java");
  }

  private ModuleFile integrationTestAnnotation() {
    return file("src/test/resources/projects/files/IntegrationTest.java", "src/test/java/com/jhipster/test/IntegrationTest.java");
  }
}
