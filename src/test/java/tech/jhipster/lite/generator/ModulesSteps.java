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

    addPomToProject(project.getFolder());

    post(moduleUrl, JsonHelper.writeAsString(project));
  }

  @When("I get module {string} properties definition")
  public void getModulePropertieDefinition(String moduleSlug) {
    rest.getForEntity(moduleUrl(moduleSlug), Void.class);
  }

  @When("I apply {string} module to default project with package json")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug, Map<String, Object> properties) {
    String projectFolder = newTestFolder();

    addPackageJsonToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, properties));
  }

  @When("I apply {string} module to default project with package json without properties")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug) {
    String projectFolder = newTestFolder();

    addPackageJsonToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, null));
  }

  @When("I apply {string} module without properties to last project")
  public void applyModuleForLastProject(String moduleSlug) {
    post(applyModuleUrl(moduleSlug), buildModuleQuery(lastProjectFolder(), null));
  }

  @When("I apply {string} module to default project with maven file without properties")
  public void applyModuleForDefaultProjectWithMavenFileWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProjectWithMavenFile(moduleSlug, null);
  }

  @When("I apply {string} module to default project with maven file")
  public void applyModuleForDefaultProjectWithMavenFile(String moduleSlug, Map<String, Object> properties) {
    String projectFolder = newTestFolder();

    addPomToProject(projectFolder);

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
    return moduleUrl(moduleSlug) + "/apply-patch";
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

  private static void addPackageJsonToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/node/package.json", "package.json");
  }

  private static void addPomToProject(String folder) {
    addFileToProject(folder, "src/test/resources/projects/init-maven/pom.xml", "pom.xml");
  }

  private static void addFileToProject(String folder, String source, String destination) {
    Path folderPath = Paths.get(folder);
    try {
      Files.createDirectories(folderPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path filePath = folderPath.resolve(destination);
    try {
      Files.copy(Paths.get(source), filePath);
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
