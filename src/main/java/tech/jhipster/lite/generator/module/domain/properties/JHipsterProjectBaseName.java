package tech.jhipster.lite.generator.module.domain.properties;

import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

public record JHipsterProjectBaseName(String name) {
  private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");
  private static final String DEFAULT_NAME = "jhipster";

  public static final JHipsterProjectBaseName DEFAULT = new JHipsterProjectBaseName(DEFAULT_NAME);

  public JHipsterProjectBaseName(String name) {
    this.name = buildName(name);
  }

  private String buildName(String name) {
    if (StringUtils.isBlank(name)) {
      return DEFAULT_NAME;
    }

    assertValidName(name);

    return name;
  }

  private void assertValidName(String name) {
    if (!NAME_PATTERN.matcher(name).matches()) {
      throw new InvalidProjectBaseNameException();
    }
  }

  public String get() {
    return name();
  }

  public String capitalized() {
    return StringUtils.capitalize(name());
  }
}
