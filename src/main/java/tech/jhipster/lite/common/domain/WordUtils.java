package tech.jhipster.lite.common.domain;

import com.google.common.base.CaseFormat;
import tech.jhipster.lite.error.domain.Assert;

public class WordUtils {

  public static final String LF = "\n";
  public static final String CRLF = "\r\n";

  public static final int DEFAULT_INDENTATION = 2;
  public static final String DQ = "\"";

  public static final String OB = "\\{";
  public static final String CB = "\\}";

  public static final String COMA = ",";
  public static final String O_BRACKET = "[";
  public static final String C_BRACKET = "]";

  private static final String VALUE_FIELD = "value";

  private WordUtils() {}

  public static String kebabCase(String value) {
    Assert.notBlank(VALUE_FIELD, value);

    return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, value);
  }

  public static String upperFirst(String value) {
    Assert.notBlank(VALUE_FIELD, value);

    StringBuilder result = new StringBuilder();
    result.append(value.substring(0, 1).toUpperCase());
    result.append(value.substring(1));
    return result.toString();
  }

  public static String lowerFirst(String value) {
    Assert.notBlank(VALUE_FIELD, value);
    return value.substring(0, 1).toLowerCase() + value.substring(1);
  }

  public static String indent(int times) {
    return indent(times, DEFAULT_INDENTATION);
  }

  public static String indent(int times, int spaceNumber) {
    Assert.field("times", times).min(1);
    Assert.field("spaceNumber", spaceNumber).min(1);

    return " ".repeat(times * spaceNumber);
  }
}
