package tech.jhipster.lite.generator.server.springboot.broker.kafka.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.broker.kafka.application.KafkaApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class KafkaModuleConfiguration {

  private static final String TAG = "Spring Boot - Broker";
  private static final String BROKER = "broker";
  private static final String SPRING_BOOT = "spring-boot";
  private static final String SPRING = "spring";
  private static final String SERVER = "server";

  @Bean
  JHipsterModuleResource kafkaResourceInit(KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-kafka")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(TAG, "Add Kafka dependencies, with testcontainers")
      .organization(JHipsterModuleOrganization.builder().addModuleDependency(SPRING_BOOT).build())
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::init);
  }

  @Bean
  JHipsterModuleResource kafkaResourceDummyProducerConsumer(KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-kafka-dummy-producer-consumer")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(TAG, "Add dummy Kafka producer and consumer")
      .organization(kafkaDepdency())
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::addDummyProducerConsumer);
  }

  @Bean
  JHipsterModuleResource kafkaResourceAkhq(KafkaApplicationService kafkaApplicationService) {
    return JHipsterModuleResource
      .builder()
      .slug("spring-boot-kafka-akhq")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addIndentation().build())
      .apiDoc(TAG, "Add AKHQ")
      .organization(kafkaDepdency())
      .tags(SERVER, SPRING, SPRING_BOOT, BROKER)
      .factory(kafkaApplicationService::addAkhq);
  }

  private JHipsterModuleOrganization kafkaDepdency() {
    return JHipsterModuleOrganization.builder().addModuleDependency("spring-boot-kafka").build();
  }
}
