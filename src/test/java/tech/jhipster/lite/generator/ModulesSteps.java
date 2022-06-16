package tech.jhipster.lite.generator;

import static tech.jhipster.lite.cucumber.CucumberAssertions.*;
import static tech.jhipster.lite.generator.ProjectsSteps.*;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

public class ModulesSteps {

  @Autowired
  private TestRestTemplate rest;

  private static final String MODULE_PROPERTIES_TEMPLATE =
    """
      {
        "projectFolder": "{PROJECT_FOLDER}",
        "properties": {{ PROPERTIES }}
      }
      """;

  @When("I apply legacy module {string} to default project")
  public void legacyApplyModuleForDefaultProject(String moduleUrl) {
    legacyApplyModulesForDefaultProject(List.of(moduleUrl));
  }

  @When("I apply legacy modules to default project")
  public void legacyApplyModulesForDefaultProject(List<String> modulesUrls) {
    ProjectDTO project = newDefaultProjectDto();

    modulesUrls.forEach(moduleUrl -> post(moduleUrl, JsonHelper.writeAsString(project)));
  }

  @When("I apply legacy module {string} to default project with maven file")
  public void legacyApplyModuleForDefaultProjectWithMavenFile(String moduleUrl) {
    ProjectDTO project = newDefaultProjectDto();

    addPomToproject(project.getFolder());

    post(moduleUrl, JsonHelper.writeAsString(project));
  }

  @When("I get module {string} properties definition")
  public void getModulePropertieDefinition(String moduleSlug) {
    rest.getForEntity(moduleUrl(moduleSlug), Void.class);
  }

  @When("I apply {string} module to default project with maven file")
  public void applyModuleForDefaultProjectWithMavenFile(String moduleSlug, Map<String, Object> properties) {
    String projectFolder = newTestFolder();

    addPomToproject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, properties));
  }

  @When("I apply {string} module to default project without properties")
  public void applyModuleForDefaultProjectWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProject(moduleSlug, null);
  }

  @When("I apply {string} module to default project")
  public void applyModuleForDefaultProject(String moduleSlug, Map<String, Object> properties) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, properties));
  }

  private String applyModuleUrl(String moduleSlug) {
    return moduleUrl(moduleSlug) + "/apply";
  }

  private String moduleUrl(String moduleSlug) {
    return "/api/modules/" + moduleSlug;
  }

  private String buildModuleQuery(String projectFolder, Map<String, Object> properties) {
    return MODULE_PROPERTIES_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{{ PROPERTIES }}", buildModuleProperties(properties));
  }

  private String buildModuleProperties(Map<String, Object> properties) {
    if (properties == null) {
      return "null";
    }

    return properties
      .entrySet()
      .stream()
      .map(entry -> "\"" + entry.getKey() + "\":" + buildPropertyValue(entry.getValue()))
      .collect(Collectors.joining(",", "{", "}"));
  }

  private String buildPropertyValue(Object value) {
    if (value == null) {
      return "null";
    }

    if (value instanceof Boolean booleanValue) {
      return Boolean.toString(booleanValue);
    }

    if (value instanceof Integer integerValue) {
      return String.valueOf(integerValue);
    }

    return "\"" + value.toString() + "\"";
  }

  private static void addPomToproject(String folder) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folderPath.resolve("pom.xml");
    try {
      Files.copy(Paths.get("src/test/resources/projects/maven/pom.xml"), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }

  private void post(String uri, String content) {
    rest.exchange(uri, HttpMethod.POST, new HttpEntity<>(content, jsonHeaders()), Void.class);
  }

  private HttpHeaders jsonHeaders() {
    HttpHeaders headers = new HttpHeaders();

    headers.setAccept(List.of(MediaType.APPLICATION_JSON));
    headers.setContentType(MediaType.APPLICATION_JSON);

    return headers;
  }

  @Then("I should have properties definitions")
  public void shouldHaveModulePropertiesDefintions(List<Map<String, Object>> propertiesDefintion) {
    assertThatLastResponse().hasOkStatus().hasElement("$.definitions").containingExactly(propertiesDefintion);
  }
}
