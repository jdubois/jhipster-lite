package tech.jhipster.lite.generator.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;

@UnitTest
class RegexMatcherTest {

  @Test
  void shouldNotBuildWithInvalidPattern() {
    assertThatThrownBy(() -> new RegexMatcher("{")).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotMatchNotMatchingPattern() {
    assertThat(new RegexMatcher("pouet").notMatchIn("toto")).isTrue();
  }

  @Test
  void shouldMatchMatchingPattern() {
    assertThat(new RegexMatcher("pouet").notMatchIn("pouet")).isFalse();
  }
}
