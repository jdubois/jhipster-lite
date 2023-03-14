package tech.jhipster.lite.generator.server.springboot.database.redis.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.server.springboot.database.redis.domain.RedisModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.docker.DockerImages;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@Service
public class RedisApplicationService {

  private final RedisModuleFactory factory;

  public RedisApplicationService(DockerImages dockerImages) {
    factory = new RedisModuleFactory(dockerImages);
  }

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    return factory.buildModule(properties);
  }
}
