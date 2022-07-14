package tech.jhipster.lite.generator.client.tools.cypress.domain;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.common.domain.ClientCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectFilesAsserter;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class CypressDomainServiceTest {

  @InjectMocks
  private CypressDomainService cypressDomainService;

  @Mock
  private NpmService npmService;

  @Mock
  private ProjectRepository projectRepository;

  @Mock
  private ClientCommonService clientCommonService;

  @Test
  void shouldAddCypress() {
    Project project = tmpProject();
    when(npmService.getVersionInCommon("cypress")).thenReturn(Optional.of("0.0.0"));
    when(npmService.getVersionInCommon("eslint-plugin-cypress")).thenReturn(Optional.of("1.1.1"));

    cypressDomainService.init(project);

    verify(npmService, times(2)).addDevDependency(any(Project.class), anyString(), anyString());
    verify(npmService).addDevDependency(project, "cypress", "0.0.0");
    verify(npmService).addDevDependency(project, "eslint-plugin-cypress", "1.1.1");

    verify(npmService, times(4)).addScript(any(Project.class), anyString(), anyString());
    verify(npmService).addScript(project, "e2e", "npm run test:component");
    verify(npmService).addScript(project, "e2e:headless", "npm run test:component:headless");
    verify(npmService).addScript(project, "test:component", "cypress open --config-file src/test/javascript/integration/cypress-config.ts");
    verify(npmService)
      .addScript(
        project,
        "test:component:headless",
        "cypress run --headless --config-file src/test/javascript/integration/cypress-config.ts"
      );

    verify(projectRepository).add(filesCountArgument(2));

    verify(projectRepository).template(any(ProjectFile.class));
    verify(projectRepository).template(ProjectFilesAsserter.filesCountArgument(1));

    verify(clientCommonService).excludeInTsconfigJson(project, "src/test/javascript/integration/**/*.ts");
  }

  @Test
  void shouldNotInit() {
    Project project = tmpProject();

    assertThatThrownBy(() -> cypressDomainService.init(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotAddDevDependencies() {
    Project project = tmpProject();

    assertThatThrownBy(() -> cypressDomainService.addDevDependencies(project)).isExactlyInstanceOf(GeneratorException.class);
  }
}
