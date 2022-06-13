package tech.jhipster.lite.generator.module.domain;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;

public record JHipsterModuleSlug(String slug) {
  private static final Pattern SLUG_FORMAT = Pattern.compile("^[a-z1-9-]+$");

  public JHipsterModuleSlug {
    Assert.notBlank("slug", slug);

    assertFormat(slug);
  }

  private static void assertFormat(String slug) {
    if (invalidFormat(slug)) {
      throw new InvalidJHipsterModuleSlugException(slug);
    }
  }

  private static boolean invalidFormat(String slug) {
    return !SLUG_FORMAT.matcher(slug).matches();
  }

  public String get() {
    return slug();
  }
}
