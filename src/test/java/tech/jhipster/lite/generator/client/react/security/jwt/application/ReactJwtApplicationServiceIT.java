package tech.jhipster.lite.generator.client.react.security.jwt.application;

import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.TestUtils.tmpProjectWithPackageJsonComplete;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.client.react.core.application.ReactApplicationService;
import tech.jhipster.lite.generator.client.react.security.jwt.domain.ReactJwt;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class ReactJwtApplicationServiceIT {

  @Autowired
  ReactApplicationService reactApplicationService;

  @Autowired
  ReactJwtApplicationService reactJwtApplicationService;

  @Test
  void shouldAddJwtReact() {
    Project project = tmpProjectWithPackageJsonComplete();

    reactApplicationService.addReact(project);
    reactJwtApplicationService.addLoginReact(project);

    ReactJwt.reactJwtFiles().forEach((file, paths) -> paths.forEach(path -> assertFileExist(project, getPath(path, file))));
  }
}
