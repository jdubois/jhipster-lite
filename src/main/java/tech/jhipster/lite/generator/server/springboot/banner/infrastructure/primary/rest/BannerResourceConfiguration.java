package tech.jhipster.lite.generator.server.springboot.banner.infrastructure.primary.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleApiDoc;
import tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModuleResource;
import tech.jhipster.lite.generator.server.springboot.banner.application.BannerApplicationService;

@Configuration
class BannerResourceConfiguration {

  private static final String TAG = "Spring Boot";

  @Bean
  JHipsterModuleResource jhipsterV7BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/jhipster-v7")
      .slug("banner-jhipster-v7")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for Angular"))
      .factory(banners::buildJHipsterV7Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterV7ReactBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/jhipster-v7-react")
      .slug("banner-jhipster-v7-react")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for React"))
      .factory(banners::buildJHipsterV7ReactBanner);
  }

  @Bean
  JHipsterModuleResource jhipsterV7VueBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/jhipster-v7-vue")
      .slug("banner-jhipster-v7-vue")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for Vue"))
      .factory(banners::buildJHipsterV7VueBanner);
  }

  @Bean
  JHipsterModuleResource jhipsterV2BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/jhipster-v2")
      .slug("banner-jhipster-v2")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v2"))
      .factory(banners::buildJHipsterV2Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterV3BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/jhipster-v3")
      .slug("banner-jhipster-v3")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v3"))
      .factory(banners::buildJHipsterV3Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterIpponBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .legacyUrl("/api/servers/spring-boot/banners/ippon")
      .slug("banner-ippon")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner for Ippon applications"))
      .factory(banners::buildIpponBanner);
  }
}
