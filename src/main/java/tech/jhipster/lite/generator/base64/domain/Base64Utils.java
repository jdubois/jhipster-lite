package tech.jhipster.lite.generator.base64.domain;

import java.security.SecureRandom;
import java.util.Base64;

public final class Base64Utils {

  private static final SecureRandom RANDOM = new SecureRandom();

  private Base64Utils() {}

  public static String getBase64Secret() {
    return Base64.getEncoder().encodeToString(getRandomHexString(64).getBytes());
  }

  private static String getRandomHexString(int length) {
    StringBuilder result = new StringBuilder();

    while (result.length() < length) {
      result.append(Integer.toHexString(RANDOM.nextInt()));
    }

    return result.substring(0, length);
  }
}
