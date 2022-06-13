package tech.jhipster.lite.generator.module.domain;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import ch.qos.logback.classic.Level;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogSpy;
import tech.jhipster.lite.UnitTest;

@UnitTest
@ExtendWith(LogSpy.class)
class JHipsterModuleContextTest {

  private final LogSpy logs;

  public JHipsterModuleContextTest(LogSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldGetDefaultIndentationFromInvalidIndentation() {
    Indentation indentation = JHipsterModuleContext
      .builder(emptyModuleBuilder())
      .put("prettierDefaultIndent", "dummy")
      .build()
      .indentation();

    logs.assertLogged(Level.INFO, "invalid indentation, using default");
    assertThat(indentation).isEqualTo(Indentation.DEFAULT);
  }

  @Test
  void shouldGetDefaultContext() {
    Map<String, Object> context = JHipsterModuleContext.builder(emptyModuleBuilder()).build().get();

    assertThat(context)
      .hasSize(4)
      .contains(
        entry("baseName", "jhipster"),
        entry("projectName", "JHipster Project"),
        entry("packageName", "com.mycompany.myapp"),
        entry("prettierDefaultIndent", 2)
      );
  }
}
