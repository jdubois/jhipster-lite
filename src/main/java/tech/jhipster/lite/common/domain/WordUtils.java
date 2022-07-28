package tech.jhipster.lite.common.domain;

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

  private WordUtils() {}

  public static String indent(int times) {
    return indent(times, DEFAULT_INDENTATION);
  }

  public static String indent(int times, int spaceNumber) {
    Assert.field("times", times).min(1);
    Assert.field("spaceNumber", spaceNumber).min(1);

    return " ".repeat(times * spaceNumber);
  }
}
