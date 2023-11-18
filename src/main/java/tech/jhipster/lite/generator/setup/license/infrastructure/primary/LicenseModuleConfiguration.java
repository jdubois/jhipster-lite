package tech.jhipster.lite.generator.setup.license.infrastructure.primary;

import static tech.jhipster.lite.generator.slug.domain.JHLiteFeatureSlug.LICENSE;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.LICENSE_APACHE;
import static tech.jhipster.lite.generator.slug.domain.JHLiteModuleSlug.LICENSE_MIT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.jhipster.lite.generator.setup.license.application.LicenseApplicationService;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;

@Configuration
public class LicenseModuleConfiguration {

  @Bean
  JHipsterModuleResource mitLicenseModule(LicenseApplicationService license) {
    return JHipsterModuleResource
      .builder()
      .slug(LICENSE_MIT)
      .withoutProperties()
      .apiDoc("License", "Add MIT license file")
      .organization(JHipsterModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildMitModule);
  }

  @Bean
  JHipsterModuleResource apacheLicenseModule(LicenseApplicationService license) {
    return JHipsterModuleResource
      .builder()
      .slug(LICENSE_APACHE)
      .withoutProperties()
      .apiDoc("License", "Add APACHE license file")
      .organization(JHipsterModuleOrganization.builder().feature(LICENSE).build())
      .tags("init", "license")
      .factory(license::buildApacheModule);
  }
}
