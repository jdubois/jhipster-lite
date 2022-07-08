package tech.jhipster.lite.module.domain.javabuildplugin;

import tech.jhipster.lite.error.domain.Assert;

public record JavaBuildPluginAdditionalElements(String additionalElements) {
  public JavaBuildPluginAdditionalElements {
    Assert.notBlank("additionalElements", additionalElements);
  }

  public String get() {
    return additionalElements();
  }
}
