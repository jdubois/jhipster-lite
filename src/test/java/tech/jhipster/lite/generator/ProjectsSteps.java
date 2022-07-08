package tech.jhipster.lite.generator;

import static org.assertj.core.api.Assertions.*;

import io.cucumber.java.en.Then;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import org.assertj.core.api.SoftAssertions;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.cucumber.CucumberAssertions;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

public class ProjectsSteps {

  private static String lastProjectFolder;

  public static ProjectDTO newDefaultProjectDto() {
    newTestFolder();

    return TestUtils.readFileToObject("json/chips.json", ProjectDTO.class).folder(lastProjectFolder);
  }

  public static String newTestFolder() {
    return lastProjectFolder = TestFileUtils.tmpDirForTest();
  }

  public static String lastProjectFolder() {
    return lastProjectFolder;
  }

  @Then("I should have files in {string}")
  public void shouldHaveFiles(String basePath, List<String> files) {
    CucumberAssertions.assertThatLastResponse().hasOkStatus();

    SoftAssertions assertions = new SoftAssertions();

    files.stream().map(file -> Paths.get(lastProjectFolder, basePath, file)).forEach(assertFileExist(assertions));

    assertions.assertAll();
  }

  private Consumer<Path> assertFileExist(SoftAssertions assertions) {
    return path -> assertions.assertThat(Files.exists(path)).as(fileNotFoundMessage(path)).isTrue();
  }

  private Supplier<String> fileNotFoundMessage(Path path) {
    return () -> "Can't find file " + path + " in project folder, found " + projectFiles();
  }

  @Then("I should not have files in {string}")
  public void shouldNotHaveFiles(String basePath, List<String> files) {
    CucumberAssertions.assertThatLastResponse().hasHttpStatusIn(200, 201);

    SoftAssertions assertions = new SoftAssertions();

    files.stream().map(file -> Paths.get(lastProjectFolder, basePath, file)).forEach(assertFileNotExist(assertions));

    assertions.assertAll();
  }

  private Consumer<Path> assertFileNotExist(SoftAssertions assertions) {
    return path -> assertions.assertThat(!Files.exists(path)).as(fileFoundMessage(path)).isTrue();
  }

  private Supplier<String> fileFoundMessage(Path path) {
    return () -> "Can find file " + path + " in project folder, found " + projectFiles();
  }

  private String projectFiles() {
    try {
      return Files.walk(Paths.get(lastProjectFolder)).filter(Files::isRegularFile).map(Path::toString).collect(Collectors.joining(", "));
    } catch (IOException e) {
      return "unreadable folder";
    }
  }

  @Then("I should have history entry for {string}")
  public void shouldHaveHistoryEntry(String slug) throws IOException {
    assertThat(Files.readString(Paths.get(lastProjectFolder, ".jhipster/history", "history.json"))).contains(slug);
  }

  @Then("I should have {string} in {string}")
  public void shouldHaveFileContent(String content, String file) throws IOException {
    assertThat(Files.readString(Paths.get(lastProjectFolder, file))).contains(content);
  }

  @Then("I should not have {string} in {string}")
  public void shouldNotHaveFileContent(String content, String file) throws IOException {
    assertThat(Files.readString(Paths.get(lastProjectFolder, file))).doesNotContain(content);
  }
}
