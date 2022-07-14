package tech.jhipster.lite.common.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.error.domain.NumberValueTooLowException;

@UnitTest
class WordUtilsTest {

  @Nested
  class KebabCaseTest {

    @Test
    void shouldKebabCase() {
      assertThat(WordUtils.kebabCase("JhipsterLite")).isEqualTo("jhipster-lite");
    }

    @Test
    void shouldKebabCaseWhenStartedWith2UpperCase() {
      assertThat(WordUtils.kebabCase("JHipsterLite")).isEqualTo("j-hipster-lite");
    }

    @Test
    void shouldNotKebabCaseForNull() {
      assertThatThrownBy(() -> WordUtils.kebabCase(null))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("value");
    }

    @Test
    void shouldNotKebabForBlank() {
      assertThatThrownBy(() -> WordUtils.kebabCase(" "))
        .isExactlyInstanceOf(MissingMandatoryValueException.class)
        .hasMessageContaining("value");
    }
  }

  @Nested
  class UpperFirstTest {

    @Test
    void shouldUpperFirst() {
      assertThat(WordUtils.upperFirst("jhipsterLite")).isEqualTo("JhipsterLite");
    }

    @Test
    void shouldUpperFirstOneLetter() {
      assertThat(WordUtils.upperFirst("j")).isEqualTo("J");
    }

    @Test
    void shouldUpperFirstTwoLetters() {
      assertThat(WordUtils.upperFirst("jh")).isEqualTo("Jh");
    }

    @Test
    void shouldNotUpperFirstWithNull() {
      assertThatThrownBy(() -> WordUtils.upperFirst(null)).isExactlyInstanceOf(MissingMandatoryValueException.class);
    }

    @Test
    void shouldNotUpperFirstWithBlank() {
      assertThatThrownBy(() -> WordUtils.upperFirst(" ")).isExactlyInstanceOf(MissingMandatoryValueException.class);
    }
  }

  @Nested
  class IndentTest {

    @Test
    void shouldIndent1Time() {
      assertThat(WordUtils.indent(1)).isEqualTo("  ");
    }

    @Test
    void shouldIndent2Times() {
      assertThat(WordUtils.indent(2)).isEqualTo("    ");
    }

    @Test
    void shouldIndent3Times() {
      assertThat(WordUtils.indent(3)).isEqualTo("      ");
    }

    @Test
    void shouldNotIndentWithNegative() {
      assertThatThrownBy(() -> WordUtils.indent(-1)).isExactlyInstanceOf(NumberValueTooLowException.class).hasMessageContaining("times");
    }

    @Test
    void shouldNotIndentWithZero() {
      assertThatThrownBy(() -> WordUtils.indent(0)).isExactlyInstanceOf(NumberValueTooLowException.class).hasMessageContaining("times");
    }
  }

  @Nested
  class IdentWithSpaceTest {

    @Test
    void shouldIndent1TimeWith4spaces() {
      assertThat(WordUtils.indent(1, 4)).isEqualTo("    ");
    }

    @Test
    void shouldIndent2TimesWith4spaces() {
      assertThat(WordUtils.indent(2, 4)).isEqualTo("        ");
    }

    @Test
    void shouldNotIndentNegativeTimeWith4Spaces() {
      assertThatThrownBy(() -> WordUtils.indent(-1, 4)).isExactlyInstanceOf(NumberValueTooLowException.class).hasMessageContaining("times");
    }

    @Test
    void shouldNotIndent0TimeWith4Spaces() {
      assertThatThrownBy(() -> WordUtils.indent(0, 4)).isExactlyInstanceOf(NumberValueTooLowException.class).hasMessageContaining("times");
    }

    @Test
    void shouldNotIndent2TimesWithNegativeSpace() {
      assertThatThrownBy(() -> WordUtils.indent(2, -1))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining("spaceNumber");
    }

    @Test
    void shouldNotIndent2TimesWith0space() {
      assertThatThrownBy(() -> WordUtils.indent(2, 0))
        .isExactlyInstanceOf(NumberValueTooLowException.class)
        .hasMessageContaining("spaceNumber");
    }
  }
}
