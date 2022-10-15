package tech.jhipster.lite.module.domain.resource;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterPropertyExample;
import tech.jhipster.lite.module.domain.properties.JHipsterPropertyType;

@UnitTest
class JHipsterModulePropertyDefinitionTest {

  @Test
  void shouldReturnExampleThroughDefaultValue() {
    JHipsterModulePropertyDefinition propertyDefinition = JHipsterModulePropertyDefinition
      .builder()
      .type(JHipsterPropertyType.STRING)
      .mandatory(true)
      .key("name")
      .example("jhipster")
      .build();

    assertThat(propertyDefinition.example().map(JHipsterPropertyExample::example)).contains("jhipster");
  }
}
