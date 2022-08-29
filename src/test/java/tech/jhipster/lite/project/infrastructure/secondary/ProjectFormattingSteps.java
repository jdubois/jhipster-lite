package tech.jhipster.lite.project.infrastructure.secondary;

import static org.mockito.Mockito.*;

import io.cucumber.java.en.Then;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectFormattingSteps {

  @Autowired
  private ProjectFormatter projectFormatter;

  @Then("I should have formatted project")
  public void shouldHaveFormattedProject() {
    verify(projectFormatter).format(any());
  }
}
