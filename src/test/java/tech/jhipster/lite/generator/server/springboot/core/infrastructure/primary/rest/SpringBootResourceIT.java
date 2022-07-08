package tech.jhipster.lite.generator.server.springboot.core.infrastructure.primary.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.List;
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
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
@AutoConfigureMockMvc
class SpringBootResourceIT {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldAddSpringBoot() throws Exception {
    ProjectDTO projectDTO = TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(TestFileUtils.tmpDirForTest());
    Project project = ProjectDTO.toProject(projectDTO);
    TestJHipsterModules.applyInit(project);
    TestJHipsterModules.applyMaven(project);

    mockMvc
      .perform(
        post("/api/servers/spring-boot").contentType(MediaType.APPLICATION_JSON).content(TestUtils.convertObjectToJsonBytes(projectDTO))
      )
      .andExpect(status().isOk());

    String projectPath = projectDTO.getFolder();
    assertFileExist(projectPath, POM_XML);
    assertFileContent(projectPath, POM_XML, List.of("<groupId>tech.jhipster.chips</groupId>", "<artifactId>chips</artifactId>"));
    assertFileContent(
      projectPath,
      POM_XML,
      List.of("<groupId>org.springframework.boot</groupId>", "<artifactId>spring-boot-starter</artifactId>")
    );

    assertFileExist(project, "src/main/java/tech/jhipster/chips/ChipsApp.java");
    assertFileExist(project, "src/test/java/tech/jhipster/chips/ChipsAppTest.java");

    assertFileExist(project, "src/main/resources/config/application.properties");
  }
}
