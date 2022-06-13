package tech.jhipster.lite.generator.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(LogSpy.class)
class JHipsterModuleOptionalReplacementsTest {

  private final LogSpy logs;

  public JHipsterModuleOptionalReplacementsTest(LogSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownFile() {
    assertThatCode(() -> replaceIn("unknown")).doesNotThrowAnyException();

    logs.assertLogged(Level.DEBUG, "no replacement done");
  }

  @Test
  void shouldSilentlyNotApplyReplacementOnUnknownCurrentValue() {
    assertThatCode(() -> replaceIn("maven/pom.xml")).doesNotThrowAnyException();
  }

  private static void replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleForProject(JHipsterModuleProperties.defaultProperties(folder));

    JHipsterModuleOptionalReplacements.builder(module).in(file).add(new TextMatcher("old"), "new").and().build().apply(folder);
  }
}
