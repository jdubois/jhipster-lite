package tech.jhipster.lite.generator.client.react.security.jwt.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwt;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class ReactJwtApplicationServiceIT {

  @Autowired
  private ReactJwtApplicationService reactJwtApplicationService;

  @Test
  void shouldAddJwtReact() {
    Project project = tmpProjectWithPackageJsonComplete();

    TestJHipsterModules.applyReact(project);
    reactJwtApplicationService.addLoginReact(project);

    ReactJwt.reactJwtFiles().forEach((file, paths) -> paths.forEach(path -> assertFileExist(project, getPath(path, file))));
  }
}
