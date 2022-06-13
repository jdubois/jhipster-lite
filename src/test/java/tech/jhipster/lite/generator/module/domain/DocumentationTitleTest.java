package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class DocumentationTitleTest {

  @Test
  void shouldCleanFilename() {
    assertThat(new DocumentationTitle("Special chars & $ éè").filename()).isEqualTo("special-chars-ee");
  }
}
