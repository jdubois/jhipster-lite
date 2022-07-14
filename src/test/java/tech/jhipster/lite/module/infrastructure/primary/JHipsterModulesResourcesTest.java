package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.infrastructure.primary.JHipsterModulesResourceFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

@UnitTest
class JHipsterModulesResourcesTest {

  @Test
  void shouldNotBuildWithoutResources() {
    assertThatThrownBy(() -> new JHipsterModulesResources(List.of()))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("modulesResources");
  }

  @Test
  void shouldNotBuildWithDuplicatedSlug() {
    JHipsterModuleResource resource = defaultModuleResourceBuilder().slug("dummy").build();

    assertThatThrownBy(() -> new JHipsterModulesResources(List.of(resource, resource))).isExactlyInstanceOf(DuplicatedSlugException.class);
  }
}
