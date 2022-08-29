package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class RestJHipsterModulePropertiesDefinitionTest {

  @Test
  void shouldSerializeToJson() {
    assertThat(JsonHelper.writeAsString(RestJHipsterModulePropertiesDefinition.from(propertiesDefinition()))).isEqualTo(json());
  }

  static String json() {
    return (
      "{\"definitions\":[" +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"packageName\",\"description\":\"Base java package\",\"example\":\"tech.jhipster.lite\",\"order\":-300}," +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"projectName\",\"description\":\"Project full name\",\"example\":\"JHipster Project\",\"order\":-200}," +
      "{\"type\":\"STRING\",\"mandatory\":true,\"key\":\"baseName\",\"description\":\"Project short name (only letters and numbers)\",\"example\":\"jhipster\",\"order\":-100}," +
      "{\"type\":\"BOOLEAN\",\"mandatory\":true,\"key\":\"mandatoryBoolean\",\"order\":0}," +
      "{\"type\":\"INTEGER\",\"mandatory\":true,\"key\":\"mandatoryInteger\",\"order\":0}," +
      "{\"type\":\"BOOLEAN\",\"mandatory\":false,\"key\":\"optionalBoolean\",\"order\":0}," +
      "{\"type\":\"STRING\",\"mandatory\":false,\"key\":\"optionalString\",\"order\":0}," +
      "{\"type\":\"INTEGER\",\"mandatory\":false,\"key\":\"indentSize\",\"description\":\"Number of spaces in indentation\",\"example\":\"2\",\"order\":500}]}"
    );
  }
}
