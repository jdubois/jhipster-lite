package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;

@UnitTest
class RegexReplacerTest {

  @Test
  void shouldNotBuildWithInvalidPattern() {
    assertThatThrownBy(() -> new RegexReplacer("{")).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotMatchNotMatchingPattern() {
    assertThat(new RegexReplacer("pouet").notMatchIn("toto")).isTrue();
  }

  @Test
  void shouldMatchMatchingPattern() {
    assertThat(new RegexReplacer("pouet").notMatchIn("pouet")).isFalse();
  }

  @Test
  void shouldGetPatternAsSearchMatcher() {
    assertThat(new RegexReplacer("pouet").searchMatcher()).isEqualTo("pouet");
  }
}
