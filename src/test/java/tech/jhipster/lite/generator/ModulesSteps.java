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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import tech.jhipster.lite.GitTestUtil;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

public class ModulesSteps {

  @Autowired
  private TestRestTemplate rest;

  private static final String MODULE_APPLICATION_TEMPLATE =
    """
      {
      "projectFolder": "{PROJECT_FOLDER}",
      "properties": {{ PROPERTIES }}
      }
      """;

  private static final String MODULE_APPLY_AND_COMMIT_TEMPLATE =
    """
      {
      "projectFolder": "{PROJECT_FOLDER}",
      "commit": true,
      "properties": {{ PROPERTIES }}
      }
      """;

  private static final String DEFAULT_MODULE_PROPERTIES_TEMPLATE =
    """
      {
        "projectFolder": "{PROJECT_FOLDER}",
        "properties": {
          "projectName": "Chips Project",
          "baseName": "chips",
          "packageName": "tech.jhipster.chips",
          "serverPort": 8080
        }
      }
      """;

  @When("I apply legacy module {string} to default project")
  public void legacyApplyModuleForDefaultProject(String moduleUrl) {
    ProjectDTO project = newDefaultProjectDto();

    post(moduleUrl, JsonHelper.writeAsString(project));
  }

  @When("I apply modules to default project")
  public void applyModulesForDefaultProject(List<String> modulesSlugs) {
    String projectFolder = newTestFolder();

    String query = DEFAULT_MODULE_PROPERTIES_TEMPLATE.replace("{PROJECT_FOLDER}", projectFolder);

    modulesSlugs.forEach(slug -> post(applyModuleUrl(slug), query));
  }

  @When("I get module {string} properties definition")
  public void getModulePropertieDefinition(String moduleSlug) {
    rest.getForEntity(moduleUrl(moduleSlug), Void.class);
  }

  @When("I apply {string} module to default project with package json")
  public void applyModuleForDefaultProjectWithPackageJson(String moduleSlug, Map<String, String> properties) {
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
  public void applyModuleForDefaultProjectWithMavenFile(String moduleSlug, Map<String, String> properties) {
    String projectFolder = newTestFolder();

    addPomToProject(projectFolder);

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, properties));
  }

  @When("I apply {string} module to default project without properties")
  public void applyModuleForDefaultProjectWithoutProperties(String moduleSlug) {
    applyModuleForDefaultProject(moduleSlug, null);
  }

  @When("I apply and commit {string} module to default project")
  public void applyAndCommitModuleForDefaultProject(String moduleSlug, Map<String, String> properties) throws IOException {
    String projectFolder = newTestFolder();

    Path projectPath = Paths.get(projectFolder);
    Files.createDirectories(projectPath);

    loadGitConfig(projectPath);

    String query = MODULE_APPLY_AND_COMMIT_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{{ PROPERTIES }}", buildModuleProperties(properties));

    post(applyModuleUrl(moduleSlug), query);
  }

  private void loadGitConfig(Path project) {
    GitTestUtil.execute(project, "init");
    GitTestUtil.execute(project, "config", "init.defaultBranch", "main");
    GitTestUtil.execute(project, "config", "user.email", "\"test@jhipster.com\"");
    GitTestUtil.execute(project, "config", "user.name", "\"Test\"");
  }

  @When("I apply {string} module to default project")
  public void applyModuleForDefaultProject(String moduleSlug, Map<String, String> properties) {
    String projectFolder = newTestFolder();

    post(applyModuleUrl(moduleSlug), buildModuleQuery(projectFolder, properties));
  }

  private String applyModuleUrl(String moduleSlug) {
    return moduleUrl(moduleSlug) + "/apply-patch";
  }

  private String moduleUrl(String moduleSlug) {
    return "/api/modules/" + moduleSlug;
  }

  private String buildModuleQuery(String projectFolder, Map<String, String> properties) {
    return MODULE_APPLICATION_TEMPLATE
      .replace("{PROJECT_FOLDER}", projectFolder)
      .replace("{{ PROPERTIES }}", buildModuleProperties(properties));
  }

  private String buildModuleProperties(Map<String, String> properties) {
    if (properties == null) {
      return "null";
    }

    return properties
      .entrySet()
      .stream()
      .map(entry -> "\"" + entry.getKey() + "\":" + buildPropertyValue(entry.getValue()))
      .collect(Collectors.joining(",", "{", "}"));
  }

  private String buildPropertyValue(String value) {
    if (value == null) {
      return "null";
    }

    if ("true".equals(value)) {
      return "true";
    }

    if ("false".equals(value)) {
      return "false";
    }

    if (StringUtils.isNumeric(value)) {
      return String.valueOf(value);
    }

    return "\"" + value + "\"";
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
