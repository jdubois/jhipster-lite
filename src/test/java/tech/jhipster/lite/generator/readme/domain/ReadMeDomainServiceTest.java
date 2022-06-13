package tech.jhipster.lite.generator.readme.domain;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static tech.jhipster.lite.TestUtils.tmpProject;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class ReadMeDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private ReadMeDomainService readMeDomainService;

  @Test
  void shouldAddSection() {
    Project project = tmpProject();

    readMeDomainService.addSection(
      project,
      "## Apache Kafka",
      """
      ## Apache Kafka

      Description of the tasks to be launched.
      """
    );

    verify(projectRepository).containsRegexp(any(Project.class), anyString(), anyString(), anyString());
    verify(projectRepository).replaceText(any(Project.class), anyString(), anyString(), anyString(), anyString());
  }
}
