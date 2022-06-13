package tech.jhipster.lite.generator.module.domain.javadependency;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class CurrentJavaDependenciesVersionsTest {

  @Test
  void shouldNotGetUnknownDependency() {
    assertThatThrownBy(() -> currentJavaDependenciesVersion().get(new VersionSlug("unknown")))
      .isExactlyInstanceOf(UnknownJavaVersionSlugException.class);
  }
}
