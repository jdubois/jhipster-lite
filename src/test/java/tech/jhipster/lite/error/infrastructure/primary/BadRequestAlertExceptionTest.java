package tech.jhipster.lite.error.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class BadRequestAlertExceptionTest {

  @Test
  void shouldBuild() {
    BadRequestAlertException exception = new BadRequestAlertException("chips", "beer", "validation");

    assertThat(exception.getTitle()).isEqualTo("chips");
    assertThat(exception.getEntityName()).isEqualTo("beer");
    assertThat(exception.getErrorKey()).isEqualTo("validation");
    assertThat(exception.getParameters()).containsExactlyInAnyOrderEntriesOf(Map.of("message", "error.validation", "params", "beer"));
  }
}
