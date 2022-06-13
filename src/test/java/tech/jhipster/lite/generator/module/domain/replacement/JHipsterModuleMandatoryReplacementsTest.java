package tech.jhipster.lite.generator.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class JHipsterModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownFile() {
    assertThatThrownBy(() -> replaceIn("unknown")).isExactlyInstanceOf(ReplacementErrorException.class);
  }

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("maven/pom.xml")).isExactlyInstanceOf(UnknownCurrentValueException.class);
  }

  private static void replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleForProject(JHipsterModuleProperties.defaultProperties(folder));

    JHipsterModuleMandatoryReplacements.builder(module).in(file).add(new TextMatcher("old"), "new").and().build().apply(folder);
  }
}
