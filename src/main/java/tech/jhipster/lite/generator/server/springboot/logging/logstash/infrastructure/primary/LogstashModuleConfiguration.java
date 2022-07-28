package tech.jhipster.lite.generator.server.springboot.logging.logstash.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.logging.logstash.application.LogstashApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.infrastructure.primary.JHipsterModuleResource;

@Configuration
class LogstashModuleConfiguration {

  @Bean
  JHipsterModuleResource logstashModule(LogstashApplicationService logstash) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/log-tools/logstash")
      .slug("logstash")
      .propertiesDefinition(JHipsterModulePropertiesDefinition.builder().addBasePackage().addIndentation().build())
      .apiDoc(new JHipsterModuleApiDoc("Spring Boot - Logging", "Add Logstash TCP appender"))
      .tags("server", "logstash", "spring", "spring-boot")
      .factory(logstash::buildModule);
  }
}
