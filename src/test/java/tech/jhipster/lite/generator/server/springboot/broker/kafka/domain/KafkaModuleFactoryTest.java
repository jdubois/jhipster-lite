package tech.jhipster.lite.generator.server.springboot.broker.kafka.domain;

import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestFileUtils.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.docker.domain.DockerImage;
import tech.jhipster.lite.docker.domain.DockerImages;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
@ExtendWith(MockitoExtension.class)
class KafkaModuleFactoryTest {

  @Mock
  private DockerImages dockerImages;

  @InjectMocks
  private KafkaModuleFactory factory;

  @Test
  void shouldBuildKafkaModuleInit() {
    when(dockerImages.get("confluentinc/cp-zookeeper")).thenReturn(new DockerImage("confluentinc/cp-zookeeper", "1.0.0"));
    when(dockerImages.get("confluentinc/cp-kafka")).thenReturn(new DockerImage("confluentinc/cp-kafka", "1.0.0"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleInit(properties);

    assertThatModuleWithFiles(
      module,
      pomFile(),
      new ModuleFile(
        "src/main/resources/generator/server/springboot/core/IntegrationTest.java.mustache",
        "src/test/java/com/jhipster/test/IntegrationTest.java"
      ),
      readmeFile()
    )
      .createFile("pom.xml")
      .containing("<artifactId>kafka-clients</artifactId>")
      .containing("<artifactId>kafka</artifactId>")
      .and()
      .createFile("src/main/docker/kafka.yml")
      .and()
      .createFile("src/main/resources/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("kafka.bootstrap-servers=localhost:9092")
      .containing("kafka.consumer.'[key.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[value.deserializer]'=org.apache.kafka.common.serialization.StringDeserializer")
      .containing("kafka.consumer.'[group.id]'=myapp")
      .containing("kafka.consumer.'[auto.offset.reset]'=earliest")
      .containing("kafka.producer.'[key.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.producer.'[value.serializer]'=org.apache.kafka.common.serialization.StringSerializer")
      .containing("kafka.polling.timeout=10000")
      .and()
      .createFile("src/test/java/com/jhipster/test/KafkaTestContainerExtension.java")
      .and()
      .createFile("src/test/java/com/jhipster/test/IntegrationTest.java")
      .containing("@ExtendWith(KafkaTestContainerExtension.class)")
      .and()
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/config/kafka/KafkaProperties.java")
      .and()
      .createFile("src/test/java/com/jhipster/test/technical/infrastructure/config/kafka/KafkaPropertiesTest.java")
      .and()
      .createFile("src/main/java/com/jhipster/test/technical/infrastructure/config/kafka/KafkaConfiguration.java")
      .and()
      .createPrefixedFiles("documentation", "apache-kafka.md")
      .createFile("README.md")
      .containing("[Apache Kafka](documentation/apache-kafka.md)")
      .and();
  }

  @Test
  void shouldBuildKafkaModuleDummyProducerConsumer() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleDummyProducerConsumer(properties);

    var dummyProducerPath = "dummy/infrastructure/secondary/kafka/producer";
    var dummyConsumerPath = "dummy/infrastructure/primary/kafka/consumer";

    assertThatModuleWithFiles(module, pomFile())
      .createFile("src/main/resources/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .createFile("src/test/resources/config/application.properties")
      .containing("kafka.topic.dummy=queue.myapp.dummy")
      .and()
      .createPrefixedFiles(
        "src/main/java/com/jhipster/test",
        dummyProducerPath + "/DummyProducer.java",
        dummyConsumerPath + "/AbstractConsumer.java",
        dummyConsumerPath + "/DummyConsumer.java"
      )
      .createPrefixedFiles(
        "src/test/java/com/jhipster/test",
        dummyProducerPath + "/DummyProducerTest.java",
        dummyProducerPath + "/DummyProducerIT.java",
        dummyConsumerPath + "/DummyConsumerTest.java",
        dummyConsumerPath + "/DummyConsumerIT.java"
      );
  }

  @Test
  void shouldBuildKafkaModuleAkhq() {
    when(dockerImages.get("tchiotludo/akhq")).thenReturn(new DockerImage("tchiotludo/akhq", "1.0.0"));

    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .build();

    JHipsterModule module = factory.buildModuleAkhq(properties);

    assertThatModuleWithFiles(module, pomFile()).createFile("src/main/docker/akhq.yml").and();
  }
}
