package tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityAssertFiles.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;
import tech.jhipster.lite.generator.project.infrastructure.secondary.GitUtils;
import tech.jhipster.lite.generator.server.javatool.base.application.JavaBaseApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.security.jwt.application.JwtSecurityApplicationService;
import tech.jhipster.lite.generator.server.springboot.mvc.web.application.SpringBootMvcApplicationService;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class JwtSecurityResourceIT {

  @Autowired
  private JavaBaseApplicationService javaBaseApplicationService;

  @Autowired
  private SpringBootMvcApplicationService springBootMvcApplicationService;

  @Autowired
  private JwtSecurityApplicationService jwtSecurityApplicationService;

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldInit() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    projectDTO.folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    GitUtils.init(project.getFolder());
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    javaBaseApplicationService.build(projectDTO.toModuleProperties());
    TestJHipsterModules.applySpringBootCore(project);
    springBootMvcApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/security-systems/jwt")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertPomXmlProperties(project);
    assertJwtSecurityFilesExists(project);
    assertJwtSecurityProperties(project);
  }

  @Test
  void shouldAddBasicAuth() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class);
    projectDTO.folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    GitUtils.init(project.getFolder());
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);
    javaBaseApplicationService.build(projectDTO.toModuleProperties());
    TestJHipsterModules.applySpringBootCore(project);
    springBootMvcApplicationService.init(project);
    jwtSecurityApplicationService.init(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot/security-systems/jwt/basic-auth")
          .contentType(MediaType.APPLICATION_JSON)
          .content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    assertBasicAuthJavaFiles(project);
    assertBasicAuthProperties(project);
  }
}
