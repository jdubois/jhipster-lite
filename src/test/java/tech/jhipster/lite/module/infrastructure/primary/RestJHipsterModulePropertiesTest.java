package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterProjectFolderFactory;

@UnitTest
class RestJHipsterModulePropertiesTest {

  private final JHipsterProjectFolderFactory jHipsterProjectFolderFactory = mock(JHipsterProjectFolderFactory.class);

  @Test
  void shouldNotConvertToPropertiesWithInvalidProjectFolder() {
    when(jHipsterProjectFolderFactory.isInvalid("/test")).thenReturn(true);

    assertThatThrownBy(() -> JsonHelper.readFromJson(json(), RestJHipsterModuleProperties.class).toDomain(jHipsterProjectFolderFactory))
      .isExactlyInstanceOf(InvalidProjectFolderException.class);
  }

  @Test
  void shouldConvertToProperties() {
    assertThat(JsonHelper.readFromJson(json(), RestJHipsterModuleProperties.class).toDomain(jHipsterProjectFolderFactory))
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
