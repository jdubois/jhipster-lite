package tech.jhipster.lite.generator.ci.gitlab.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GitLabCiModuleFactory {

  private static final JHipsterSource SOURCE = from("ci/gitlab");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties).files().add(SOURCE.template(".gitlab-ci-maven.yml.mustache"), to(".gitlab-ci.yml")).and().build();
  }
}
