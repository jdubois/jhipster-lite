package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;
import static tech.jhipster.lite.module.domain.replacement.ReplacementCondition.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.Logs;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.LogsSpyExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.GeneratedProjectRepository;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ContentReplacers;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacementsFactory;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacementsFactory;
import tech.jhipster.lite.module.domain.replacement.MandatoryReplacementException;
import tech.jhipster.lite.module.domain.replacement.TextReplacer;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class FileSystemReplacerTest {

  private static final FileSystemReplacer replacer = new FileSystemReplacer();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldHandleMandatoryReplacementError() {
    String path = TestFileUtils.tmpDirForTest();

    assertThatThrownBy(() ->
        replacer.handle(
          new JHipsterProjectFolder(path),
          new ContentReplacers(
            JHipsterModuleMandatoryReplacementsFactory
              .builder(emptyModuleBuilder())
              .in(new JHipsterProjectFilePath("unknown"))
              .add(new TextReplacer(always(), "old"), "new")
              .and()
              .build()
              .replacers()
              .toList()
          )
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
          new ContentReplacers(
            JHipsterModuleOptionalReplacementsFactory
              .builder(emptyModuleBuilder())
              .in(new JHipsterProjectFilePath("unknown"))
              .add(new TextReplacer(always(), "old"), "new")
              .and()
              .build()
              .buildReplacers(new JHipsterProjectFolder("dummy"), mock(GeneratedProjectRepository.class))
              .toList()
          )
        )
      )
      .doesNotThrowAnyException();

    logs.shouldHave(Level.DEBUG, "Can't apply optional replacement");
  }
}
