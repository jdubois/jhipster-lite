package tech.jhipster.lite.generator.server.springboot.banner.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.server.springboot.banner.application.BannerApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleApiDoc;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
class BannerModuleConfiguration {

  private static final String TAG = "Spring Boot - Banner";
  private static final String SERVER = "server";
  private static final String SPRING = "spring";
  private static final String SPRING_BOOT = "spring-boot";
  private static final String BANNER = "banner";

  @Bean
  JHipsterModuleResource jhipsterV7BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-jhipster-v7")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for Angular"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildJHipsterV7Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterV7ReactBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-jhipster-v7-react")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for React"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildJHipsterV7ReactBanner);
  }

  @Bean
  JHipsterModuleResource jhipsterV7VueBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-jhipster-v7-vue")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v7 for Vue"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildJHipsterV7VueBanner);
  }

  @Bean
  JHipsterModuleResource jhipsterV2BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-jhipster-v2")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v2"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildJHipsterV2Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterV3BannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-jhipster-v3")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner JHipster v3"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildJHipsterV3Banner);
  }

  @Bean
  JHipsterModuleResource jhipsterIpponBannerResource(BannerApplicationService banners) {
    return JHipsterModuleResource
      .builder()
      .slug("banner-ippon")
      .withoutProperties()
      .apiDoc(new JHipsterModuleApiDoc(TAG, "Add banner for Ippon applications"))
      .organization(organization())
      .tags(SERVER, SPRING, SPRING_BOOT, BANNER)
      .factory(banners::buildIpponBanner);
  }

  private JHipsterModuleOrganization organization() {
    return JHipsterModuleOrganization.builder().feature(BANNER).addModuleDependency(SPRING_BOOT).build();
  }
}
