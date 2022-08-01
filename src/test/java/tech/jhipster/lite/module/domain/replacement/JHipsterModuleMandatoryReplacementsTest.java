package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class JHipsterModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("src/test/resources/projects/maven/pom.xml"))
      .isExactlyInstanceOf(UnknownCurrentValueException.class);
  }

  private static String replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModuleProperties.defaultProperties(folder));

    Collection<ContentReplacer> replacements = JHipsterModuleMandatoryReplacements
      .builder(module)
      .in(file)
      .add(new TextReplacer(always(), "old"), "new")
      .and()
      .build()
      .replacements();

    String result = readContent(file);
    for (ContentReplacer replacement : replacements) {
      result = replacement.apply(result);
    }

    return result;
  }

  private static String readContent(String file) {
    try {
      return Files.readString(Paths.get(file));
    } catch (IOException e) {
      throw new AssertionError();
    }
  }
}
