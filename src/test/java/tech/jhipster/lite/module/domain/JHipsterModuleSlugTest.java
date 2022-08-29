package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterModuleSlugTest {

  @ParameterizedTest
  @ValueSource(strings = { "Invalid", "this is invalid" })
  void shouldNotBuildInvalidSlug(String slug) {
    assertThatThrownBy(() -> new JHipsterModuleSlug(slug)).isExactlyInstanceOf(InvalidJHipsterSlugException.class);
  }
}
