package tech.jhipster.lite.generator.server.springboot.banner.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.module.domain.JHipsterModule;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.server.springboot.banner.domain.BannerModuleFactory;

@Service
public class BannerApplicationService {

  private final BannerModuleFactory factory;

  public BannerApplicationService() {
    this.factory = new BannerModuleFactory();
  }

  public JHipsterModule buildJHipsterV7Banner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerJHipsterV7(properties);
  }

  public JHipsterModule buildJHipsterV7ReactBanner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerJHipsterV7React(properties);
  }

  public JHipsterModule buildJHipsterV7VueBanner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerJHipsterV7Vue(properties);
  }

  public JHipsterModule buildJHipsterV2Banner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerJHipsterV2(properties);
  }

  public JHipsterModule buildJHipsterV3Banner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerJHipsterV3(properties);
  }

  public JHipsterModule buildIpponBanner(JHipsterModuleProperties properties) {
    return factory.buildModuleBannerIppon(properties);
  }
}
