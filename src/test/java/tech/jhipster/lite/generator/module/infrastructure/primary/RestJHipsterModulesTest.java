package tech.jhipster.lite.generator.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.infrastructure.primary.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RestJHipsterModulesTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJHipsterModules.from(moduleResources()))).isEqualTo(json());
  }

  private String json() {
    return (
      "{\"categories\":[" +
      "{\"name\":\"Another tag\"," +
      "\"modules\":[{\"slug\":\"yet-another-module\",\"description\":\"Another operation\"}]}," +
      "{\"name\":\"tag\"," +
      "\"modules\":[" +
      "{\"slug\":\"another-module\",\"description\":\"operation\"}," +
      "{\"slug\":\"slug\",\"description\":\"operation\"}]}]}"
    );
  }
}
