package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.Indentation;

@UnitTest
class IndentationTest {

  @Test
  void shouldGetDefaultIndentationWithoutIndentation() {
    assertThat(Indentation.from(null)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetDefaultIndentationForZeroIndentation() {
    assertThat(Indentation.from(0)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetDefaultIndentationForNegativeIndentation() {
    assertThat(Indentation.from(-1)).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetIndentationFromActualIndentation() {
    assertThat(Indentation.from(42)).isEqualTo(new Indentation(42));
  }
}
