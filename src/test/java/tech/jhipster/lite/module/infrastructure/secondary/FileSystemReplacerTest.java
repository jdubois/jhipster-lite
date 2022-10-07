package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacements;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacements;
import tech.jhipster.lite.module.domain.replacement.MandatoryReplacementException;
import tech.jhipster.lite.module.domain.replacement.TextReplacer;

@UnitTest
@ExtendWith(LogsSpy.class)
class FileSystemReplacerTest {

  private static final FileSystemReplacer replacer = new FileSystemReplacer();

  private final LogsSpy logs;

  public FileSystemReplacerTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldHandleMandatoryReplacementError() {
    String path = TestFileUtils.tmpDirForTest();

    assertThatThrownBy(() ->
        replacer.handle(
          new JHipsterProjectFolder(path),
          JHipsterModuleMandatoryReplacements
            .builder(emptyModuleBuilder())
            .in(new JHipsterProjectFilePath("unknown"))
            .add(new TextReplacer(always(), "old"), "new")
            .and()
            .build()
        )
      )
      .isExactlyInstanceOf(MandatoryReplacementException.class);
  }

  @Test
  void shouldHandleOptionalReplacementError() {
    String path = TestFileUtils.tmpDirForTest();

    assertThatCode(() ->
        replacer.handle(
          new JHipsterProjectFolder(path),
          JHipsterModuleOptionalReplacements
            .builder(emptyModuleBuilder())
            .in(new JHipsterProjectFilePath("unknown"))
            .add(new TextReplacer(always(), "old"), "new")
            .and()
            .build()
        )
      )
      .doesNotThrowAnyException();

    logs.shouldHave(Level.DEBUG, "Can't apply optional replacement");
  }
}
