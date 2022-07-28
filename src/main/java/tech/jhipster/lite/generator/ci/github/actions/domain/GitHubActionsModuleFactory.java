package tech.jhipster.lite.generator.ci.github.actions.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class GitHubActionsModuleFactory {

  private static final JHipsterSource SOURCE = from("ci/github/actions/.github");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    return moduleBuilder(properties)
      .files()
      .add(SOURCE.template("actions/setup/action.yml"), to(".github/actions/setup/action.yml"))
      .add(SOURCE.template("workflows/github-actions-maven.yml.mustache"), to(".github/workflows/github-actions.yml"))
      .and()
      .build();
  }
}
