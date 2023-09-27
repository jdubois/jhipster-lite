package tech.jhipster.lite.project.infrastructure.secondary;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.Logs;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.LogsSpyExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.project.domain.ProjectPath;

@UnitTest
@ExtendWith(LogsSpyExtension.class)
class TraceProjectFormatterTest {

  private static final TraceProjectFormatter formatter = new TraceProjectFormatter();

  @Logs
  private LogsSpy logs;

  @Test
  void shouldTraceThatNoNpmIsInstalled() {
    formatter.format(new ProjectPath("dummy"));

    logs.shouldHave(Level.INFO, "No npm installed");
  }
}
