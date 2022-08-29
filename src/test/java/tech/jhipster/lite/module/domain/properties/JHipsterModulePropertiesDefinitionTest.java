package tech.jhipster.lite.module.domain.properties;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition.*;

import java.util.Collection;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertiesDefinition;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition;

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
