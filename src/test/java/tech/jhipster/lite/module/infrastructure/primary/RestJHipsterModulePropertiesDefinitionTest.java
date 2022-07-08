package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;

@UnitTest
class RestJHipsterModulePropertiesDefinitionTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJHipsterModulePropertiesDefinition.from(JHipsterModulesFixture.propertiesDefinition())))
      .isEqualTo(json());
  }

  private String json() {
    return (
      "{\"definitions\":[" +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"packageName\",\"description\":\"Base java package\",\"example\":\"tech.jhipster.lite\"}," +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"projectName\",\"description\":\"Project full name\",\"example\":\"JHipster Project\"}," +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"baseName\",\"description\":\"Project short name (only letters and numbers)\",\"example\":\"jhipster\"}," +
      "{\"type\":\"BOOLEAN\",\"mandatory\":true,\"key\":\"mandatoryBoolean\"}," +
      "{\"type\":\"INTEGER\",\"mandatory\":true,\"key\":\"mandatoryInteger\"}," +
      "{\"type\":\"BOOLEAN\",\"mandatory\":false,\"key\":\"optionalBoolean\"}," +
      "{\"type\":\"STRING\",\"mandatory\":false,\"key\":\"optionalString\"}," +
      "{\"type\":\"INTEGER\",\"mandatory\":false,\"key\":\"prettierDefaultIndent\",\"description\":\"Number of spaces in indentation\",\"example\":\"2\"}]}"
    );
  }
}
