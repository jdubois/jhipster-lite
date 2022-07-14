package tech.jhipster.lite.generator.project.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import com.github.mustachejava.MustacheNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;

@UnitTest
@ExtendWith(SpringExtension.class)
class ProjectLocalRepositoryTest {

  @InjectMocks
  private ProjectLocalRepository repository;

  @Test
  void shouldNotAddFileToUnknownFolder() {
    ProjectFile file = ProjectFile.forProject(tmpProject()).withSource("common", UUID.randomUUID().toString()).withSameDestination();

    assertThatThrownBy(() -> repository.add(file)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldHandleCopyError() {
    Project project = tmpProject();

    try (MockedStatic<Files> files = Mockito.mockStatic(Files.class)) {
      files.when(() -> Files.copy(any(InputStream.class), any(Path.class), any(CopyOption.class))).thenThrow(new IOException());

      ProjectFile file = ProjectFile.forProject(project).withSource("mustache", "README.txt").withSameDestination();
      assertThatThrownBy(() -> repository.add(file)).isInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldAddFiles() {
    Project project = tmpProject();

    Collection<ProjectFile> files = ProjectFile.forProject(project).all("init", "gitattributes", "gitignore");
    repository.add(files);

    assertFileExist(project, "gitattributes");
    assertFileExist(project, "gitignore");
  }

  @Test
  void shouldHandleTemplatingError() {
    Project project = Project.builder().folder(TestFileUtils.tmpDirForTest()).build();

    try (MockedStatic<MustacheUtils> mustacheUtils = Mockito.mockStatic(MustacheUtils.class)) {
      mustacheUtils.when(() -> MustacheUtils.template(anyString(), any())).thenThrow(new IOException());

      List<ProjectFile> files = List.of(ProjectFile.forProject(project).withSource("mustache", README_MD).withSameDestination());
      assertThatThrownBy(() -> repository.template(files)).isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotTemplateForUnknownFile() {
    Project project = tmpProject();
    List<ProjectFile> files = List.of(
      ProjectFile.forProject(project).withSource("mustache", "README.md.wrong.mustache").withSameDestination()
    );

    assertThatThrownBy(() -> repository.template(files)).isInstanceOf(MustacheNotFoundException.class);
  }

  @Test
  void shouldTemplatizeFile() {
    Project project = tmpProject();
    List<ProjectFile> files = List.of(ProjectFile.forProject(project).withSource("mustache", README_MD).withSameDestination());

    repository.template(files);

    assertFileExist(project, README_MD);
  }

  @Test
  void shouldTemplatizeFileWithMustacheExtension() {
    Project project = tmpProject();
    List<ProjectFile> files = List.of(ProjectFile.forProject(project).withSource("mustache", "README.md.mustache").withSameDestination());

    repository.template(files);

    assertFileExist(project, README_MD);
  }

  @Test
  void shouldTemplateWithDestinationAndDestinationFilename() {
    Project project = tmpProject();

    repository.template(
      List.of(
        ProjectFile
          .forProject(project)
          .withSource("mustache", "README.md.mustache")
          .withDestination(getPath(MAIN_RESOURCES), "FINAL-README.md")
      )
    );

    assertFileExist(project, MAIN_RESOURCES, "FINAL-README.md");
  }

  @Test
  void shouldContainsRegexp() {
    Project project = tmpProjectWithPomXml();

    boolean result = repository.containsRegexp(project, ".", POM_XML, "<artifactId>jhipster</artifactId>");

    assertThat(result).isTrue();
  }

  @Test
  void shouldNotContainsRegexp() {
    Project project = tmpProjectWithPomXml();

    boolean result = repository.containsRegexp(project, ".", POM_XML, "<artifactId>chips</artifactId>");

    assertThat(result).isFalse();
  }

  @Test
  void shouldNotContainsRegexpWithoutExistingFile() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.containsRegexp(project, ".", POM_XML, "<artifactId>chips</artifactId>"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldReplaceText() {
    Project project = tmpProjectWithPomXml();

    String oldText = """
        <name>jhipster</name>
          <description>JHipster Project</description>""";
    String newText = """
        <name>chips</name>
          <description>Chips Project</description>""";

    repository.replaceText(project, "", POM_XML, oldText, newText);

    assertFileContent(project, POM_XML, List.of("<name>chips</name>", "<description>Chips Project</description>"));
  }

  @Test
  void shouldNotReplaceText() {
    Project project = tmpProject();
    String oldText = """
        <name>jhipster</name>
          <description>JHipster Project</description>""";
    String newText = """
        <name>chips</name>
          <description>Chips Project</description>""";

    assertThatThrownBy(() -> repository.replaceText(project, "", POM_XML, oldText, newText)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldWrite() {
    Project project = tmpProject();

    repository.write(project, "hello world", "hello", "hello.world");

    assertFileExist(project, "hello/hello.world");
    assertFileContent(project, "hello/hello.world", "hello world");
  }

  @Test
  void shouldNotWriteWhenDestinationCantBeCreated() {
    Project project = tmpProject();
    repository.write(project, "hello world", ".", "hello");

    assertThatThrownBy(() -> repository.write(project, "another hello world", "hello", "hello.world"))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldNotWriteNullText() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.write(project, null, null, null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("text");
  }

  @Test
  void shouldGitInit() {
    Project project = tmpProject();

    repository.gitInit(project);

    assertFileExist(project, ".git/config");
  }

  @Test
  void shouldNotGitInit() {
    Project project = tmpProject();

    try (MockedStatic<GitUtils> gitUtils = Mockito.mockStatic(GitUtils.class)) {
      gitUtils.when(() -> GitUtils.init(anyString())).thenThrow(new InvalidConfigurationException("error"));

      assertThatThrownBy(() -> repository.gitInit(project)).isExactlyInstanceOf(GeneratorException.class);
      assertFileNotExist(project, ".git/config");
    }
  }

  @Test
  void shouldGitApplyPatch() {
    Project project = tmpProject();

    repository.gitInit(project);
    repository.gitApplyPatch(project, getPath(TEST_TEMPLATE_RESOURCES, "utils", "example.patch"));

    assertFileExist(project, "example.md");
  }

  @Test
  void shouldNotApplyPatch() {
    Project project = tmpProject();

    try (MockedStatic<GitUtils> gitUtils = Mockito.mockStatic(GitUtils.class)) {
      gitUtils.when(() -> GitUtils.apply(anyString(), anyString())).thenThrow(new InvalidConfigurationException("error"));
      String path = getPath(TEST_TEMPLATE_RESOURCES, "utils", "example.patch");
      assertThatThrownBy(() -> repository.gitApplyPatch(project, path)).isExactlyInstanceOf(GeneratorException.class);
      assertFileNotExist(project, "example.md");
    }
  }

  @Test
  void shouldRename() {
    Project project = tmpProjectWithPomXml();

    repository.rename(project, ".", POM_XML, "pom.xml.beer");

    assertFileNotExist(project, POM_XML);
    assertFileExist(project, "pom.xml.beer");
  }

  @Test
  void shouldNotRename() {
    Project project = tmpProject();

    assertThatThrownBy(() -> repository.rename(project, ".", POM_XML, "pom.xml.burger")).isExactlyInstanceOf(GeneratorException.class);

    assertFileNotExist(project, POM_XML);
  }

  @Test
  void shouldZip() {
    Project project = tmpProjectWithPomXml();

    String result = repository.zip(project);

    assertFileExist(getPath(tmpDir(), result));
  }

  @Test
  void shouldNotZipWithNonExistingFolder() {
    Project project = tmpProject();
    assertThatThrownBy(() -> repository.zip(project)).isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldDownload() {
    Project project = tmpProjectWithPomXml();
    assertThat(repository.download(project)).isNotNull();
  }

  @Test
  void shouldNotDownload() {
    Project project = tmpProjectWithPomXml();
    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(FileUtils::tmpDir).thenCallRealMethod();
      fileUtils.when(() -> FileUtils.convertFileInTmpToByte(anyString())).thenThrow(new IOException());

      assertThatThrownBy(() -> repository.download(project)).isExactlyInstanceOf(GeneratorException.class);
    }
  }
}
