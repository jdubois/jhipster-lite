package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.secondary.GitUtils;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class JwtSecurityApplicationServiceIT {

  @Autowired
  private SpringBootMvcService springBootMvcService;

  @Autowired
  private JwtSecurityApplicationService jwtSecurityApplicationService;

  @Test
  void shouldInit() throws Exception {
    Project project = tmpProject();
    GitUtils.init(project.getFolder());
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);
    springBootMvcService.init(project);

    jwtSecurityApplicationService.init(project);

    assertPomXmlProperties(project);
    assertJwtSecurityFilesExists(project);
    assertExceptionTranslatorWithSecurity(project);
    assertJwtSecurityProperties(project);
    assertLoggerInConfig(project);

    String integrationTest = "com/mycompany/myapp/IntegrationTest.java";
    assertFileContent(
      project,
      getPath(TEST_JAVA, integrationTest),
      List.of(
        "import org.springframework.boot.test.context.SpringBootTest;",
        "import org.springframework.security.test.context.support.WithMockUser;"
      )
    );
    assertFileContent(project, getPath(TEST_JAVA, integrationTest), List.of("@WithMockUser", "public @interface"));
  }

  @Test
  void shouldAddBasicAuth() throws Exception {
    Project project = tmpProject();
    GitUtils.init(project.getFolder());
    TestJHipsterModules.applyMaven(project);
    TestJHipsterModules.applySpringBootCore(project);
    springBootMvcService.init(project);
    jwtSecurityApplicationService.init(project);

    jwtSecurityApplicationService.addBasicAuth(project);

    assertBasicAuthJavaFiles(project);
    assertBasicAuthProperties(project);
  }
}
