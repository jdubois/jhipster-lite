package tech.jhipster.lite.technical.infrastructure.primary.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;

/**
 * Utility class for HTTP headers creation.
 */
final class HeaderUtil {

  private static final Logger log = LoggerFactory.getLogger(HeaderUtil.class);

  private HeaderUtil() {}

  /**
   * <p>createFailureAlert.</p>
   *
   * @param applicationName a {@link String} object.
   * @param enableTranslation a boolean.
   * @param entityName a {@link String} object.
   * @param errorKey a {@link String} object.
   * @param defaultMessage a {@link String} object.
   * @return a {@link HttpHeaders} object.
   */
  public static HttpHeaders createFailureAlert(
    String applicationName,
    boolean enableTranslation,
    String entityName,
    String errorKey,
    String defaultMessage
  ) {
    log.error("Entity processing failed, {}", defaultMessage);

    String message = enableTranslation ? "error." + errorKey : defaultMessage;

    HttpHeaders headers = new HttpHeaders();
    headers.add("X-" + applicationName + "-error", message);
    headers.add("X-" + applicationName + "-params", entityName);
    return headers;
  }
}
