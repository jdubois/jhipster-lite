package tech.jhipster.lite.generator.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RestJHipsterModulePropertiesTest {

  @Test
  void shouldConvertToProperties() {
    assertThat(JsonHelper.readFromJson(json(), RestJHipsterModuleProperties.class).toDomain())
      .usingRecursiveComparison()
      .isEqualTo(allProperties());
  }

  private static String json() {
    return """
        {
          "projectFolder": "/test",
          "properties": {
            "packageName": "tech.jhipster.chips",
            "prettierDefaultIndent": 2,
            "projectName": "JHipster project",
            "baseName": "jhipster",
            "optionalString": "optional",
            "mandatoryInteger": 42,
            "mandatoryBoolean": true,
            "optionalBoolean": true
          }
        }
          """;
  }
}
