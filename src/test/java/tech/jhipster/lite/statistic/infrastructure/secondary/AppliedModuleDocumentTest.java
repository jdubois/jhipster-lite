package tech.jhipster.lite.statistic.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.statistic.domain.AppliedModuleFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class AppliedModuleDocumentTest {

  @Test
  void shouldConvertFromAndToDomain() {
    assertThat(AppliedModuleDocument.from(appliedModule()).toDomain()).usingRecursiveComparison().isEqualTo(appliedModule());
  }
}
