package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleTags;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleTags.JHipsterModuleTagsBuilder;
import tech.jhipster.lite.shared.error.domain.StringWithWhitespacesException;

@UnitTest
class JHipsterTagsTest {

  @Test
  void shouldNotBeValidWithWhitespace() {
    JHipsterModuleTagsBuilder builder = JHipsterModuleTags.builder();

    assertThatThrownBy(() -> builder.add("my tag").build()).isInstanceOf(StringWithWhitespacesException.class);
  }
}
