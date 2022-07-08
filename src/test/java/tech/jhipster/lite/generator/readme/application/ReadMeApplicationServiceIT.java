package tech.jhipster.lite.generator.readme.application;

import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class ReadMeApplicationServiceIT {

  @Autowired
  private ReadMeApplicationService readMeApplicationService;

  @Test
  void shouldAddSection() {
    Project project = tmpProject();

    TestJHipsterModules.applyInit(project);
    readMeApplicationService.addSection(
      project,
      "## Apache Kafka",
      """
        ## Apache Kafka

        Description of the tasks to be launched.
        """
    );

    assertFileContent(project, README_MD, "Description of the tasks to be launched.");
  }

  @Test
  void shouldNotAddSection() {
    Project project = tmpProject();

    TestJHipsterModules.applyInit(project);
    readMeApplicationService.addSection(
      project,
      "## Apache Kafka",
      """
        ## Apache Kafka

        Description of the tasks to be launched.
        """
    );
    readMeApplicationService.addSection(project, "## Apache Kafka", """
        ## Apache Kafka

        Update section content.
        """);

    // Section header is already existing so it won't add a new section
    assertFileContent(project, README_MD, "Description of the tasks to be launched.");
  }
}
