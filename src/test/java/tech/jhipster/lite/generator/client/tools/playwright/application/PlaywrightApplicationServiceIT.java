package tech.jhipster.lite.generator.client.tools.playwright.application;

import static tech.jhipster.lite.TestUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class PlaywrightApplicationServiceIT {

  @Autowired
  private PlaywrightApplicationService playwrightApplicationService;

  @Test
  void shouldInit() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestJHipsterModules.applyReact(project);

    playwrightApplicationService.init(project);

    PlaywrightAssert.assertDependency(project);
    PlaywrightAssert.assertPlaywrightScripts(project);
    PlaywrightAssert.assertPlaywrightTestFiles(project);
    PlaywrightAssert.assertPlaywrightPageObjectFiles(project);
  }
}
