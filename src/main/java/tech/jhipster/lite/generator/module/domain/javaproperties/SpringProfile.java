package tech.jhipster.lite.generator.module.domain.javaproperties;

import org.apache.commons.lang3.StringUtils;

public record SpringProfile(String profile) {
  public static final SpringProfile DEFAULT = new SpringProfile(null);

  public String get() {
    return profile();
  }

  public boolean isDefault() {
    return StringUtils.isBlank(profile);
  }
}
