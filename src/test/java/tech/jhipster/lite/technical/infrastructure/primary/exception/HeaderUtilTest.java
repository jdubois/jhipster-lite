package tech.jhipster.lite.technical.infrastructure.primary.exception;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import tech.jhipster.lite.UnitTest;

@UnitTest
class HeaderUtilTest {

  @Test
  void shouldCreateFailureAlertWithTranslation() {
    HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", true, "User", "404", "Failed to find user");
    assertThat(headers.getFirst("X-myApp-error")).isEqualTo("error.404");
    assertThat(headers.getFirst("X-myApp-params")).isEqualTo("User");
  }

  @Test
  void shouldCreateFailureAlertNoTranslation() {
    HttpHeaders headers = HeaderUtil.createFailureAlert("myApp", false, "User", "404", "Failed to find user");
    assertThat(headers.getFirst("X-myApp-error")).isEqualTo("Failed to find user");
    assertThat(headers.getFirst("X-myApp-params")).isEqualTo("User");
  }
}
