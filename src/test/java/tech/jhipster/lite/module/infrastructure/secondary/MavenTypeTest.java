package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class MavenTypeTest {

  @Test
  void shouldGetEmptyTypeForNullType() {
    assertThat(MavenType.from(null)).isEmpty();
  }

  @Test
  void shouldGetEmptyTypeForUnknownType() {
    assertThat(MavenType.from("unknown")).isEmpty();
  }

  @Test
  void shouldGetTypeFromPomType() {
    assertThat(MavenType.from("pom")).contains(MavenType.POM);
  }
}
