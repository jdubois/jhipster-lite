package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.properties.JHipsterModulePropertyDefinition.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterModulePropertiesDefinitionTest {

  @Test
  void shouldDeduplicateProperties() {
    Collection<JHipsterModulePropertyDefinition> properties = JHipsterModulePropertiesDefinition
      .builder()
      .addIndentation()
      .addBasePackage()
      .addBasePackage()
      .build()
      .get();

    assertThat(properties).usingRecursiveFieldByFieldElementComparator().containsExactly(basePackageProperty(), indentationProperty());
  }
}
