package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterProjectBaseNameTest {

  @Test
  void shouldNotBuildWithInvalidProjectBaseName() {
    assertThatThrownBy(() -> new JHipsterProjectBaseName("invalid name")).isExactlyInstanceOf(InvalidProjectBaseNameException.class);
  }

  @Test
  void shouldGetDefaultPojectNameFromNullBaseName() {
    assertThat(new JHipsterProjectBaseName(null).get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetDefaultPojectBaseNameFromBlankName() {
    assertThat(new JHipsterProjectBaseName(" ").get()).isEqualTo("jhipster");
  }

  @Test
  void shouldGetProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("myProject").get()).isEqualTo("myProject");
  }

  @Test
  void shouldUncapitalizeProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("MyProject").uncapitalized()).isEqualTo("myProject");
  }

  @Test
  void shouldGetCapitalizedProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("myProject").capitalized()).isEqualTo("MyProject");
  }

  @Test
  void shouldGetKebabCaseProjectBaseName() {
    assertThat(new JHipsterProjectBaseName("MyProject3").kebabCase()).isEqualTo("my-project3");
  }
}
