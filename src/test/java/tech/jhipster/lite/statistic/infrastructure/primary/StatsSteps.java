package tech.jhipster.lite.statistic.infrastructure.primary;

import static tech.jhipster.lite.cucumber.CucumberAssertions.*;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

public class StatsSteps {

  @Autowired
  private TestRestTemplate rest;

  @When("I get statistics")
  public void getStatistics() {
    rest.getForEntity("/api/statistics", Void.class);
  }

  @Then("I should have statistics")
  public void shouldHaveStatistics(Map<String, Object> statistics) {
    assertThatLastResponse().hasOkStatus().hasResponse().containing(statistics);
  }
}
