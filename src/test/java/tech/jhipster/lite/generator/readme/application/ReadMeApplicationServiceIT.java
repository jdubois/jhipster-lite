package tech.jhipster.lite.generator.readme.application;

import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.tmpProject;
import static tech.jhipster.lite.generator.project.domain.Constants.README_MD;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.init.application.InitApplicationService;
import tech.jhipster.lite.generator.project.domain.Project;

@IntegrationTest
class ReadMeApplicationServiceIT {

  @Autowired
  InitApplicationService initApplicationService;

  @Autowired
  ReadMeApplicationService readMeApplicationService;

  @Test
  void shouldAddSection() {
    Project project = tmpProject();

    initApplicationService.addReadme(project);
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

    initApplicationService.addReadme(project);
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
