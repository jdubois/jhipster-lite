package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(LogsSpy.class)
class JHipsterModuleOptionalReplacementsTest {

  private final LogsSpy logs;

  public JHipsterModuleOptionalReplacementsTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownFile() {
    assertThatCode(() -> replaceIn("unknown")).doesNotThrowAnyException();

    logs.shouldHave(Level.DEBUG, "no replacement done");
  }

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownCurrentValue() {
    assertThatCode(() -> replaceIn("maven/pom.xml")).doesNotThrowAnyException();
  }

  private static void replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModuleProperties.defaultProperties(folder));

    JHipsterModuleOptionalReplacements.builder(module).in(file).add(new TextMatcher("old"), "new").and().build().apply(folder);
  }
}
