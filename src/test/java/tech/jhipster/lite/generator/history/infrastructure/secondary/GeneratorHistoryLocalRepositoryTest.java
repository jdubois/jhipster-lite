package tech.jhipster.lite.generator.history.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.generator.history.domain.HistoryProjectsFixture.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.generator.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.generator.history.domain.HistoryProject;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GeneratorHistoryLocalRepositoryTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private GeneratorHistoryLocalRepository generatorHistoryLocalRepository;

  @Test
  void shouldGetHistoryData() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.folder().get(), ".jhipster/history", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

      // When
      GeneratorHistoryData generatorHistoryData = generatorHistoryLocalRepository.getHistoryData(project.folder());

      // Then
      List<GeneratorHistoryValue> values = new ArrayList<>();
      values.add(new GeneratorHistoryValue("springboot-init", null));
      values.add(new GeneratorHistoryValue("java-init", Instant.parse("2022-01-22T10:11:12.000Z")));
      GeneratorHistoryData expectedGeneratorHistoryData = new GeneratorHistoryData(values);
      assertThat(generatorHistoryData).usingRecursiveComparison().isEqualTo(expectedGeneratorHistoryData);
    }
  }

  @Test
  void shouldNotGetHistoryDataWhenFileDoesNotExist() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(() -> FileUtils.getPath(any())).thenReturn("/path/history.json");
      fileUtils.when(() -> FileUtils.read(anyString())).thenThrow(new IOException());

      // When + Then
      assertThatThrownBy(() -> generatorHistoryLocalRepository.getHistoryData(project.folder()))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldNotGetHistoryDataWhenJsonIsNotDeserializable() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      fileUtils.when(() -> FileUtils.getPath(any())).thenReturn("/path/history.json");
      fileUtils.when(() -> FileUtils.read(anyString())).thenReturn("invalidJsonContent");

      // When + Then
      assertThatThrownBy(() -> generatorHistoryLocalRepository.getHistoryData(project.folder()))
        .isExactlyInstanceOf(GeneratorException.class);
    }
  }

  @Test
  void shouldAddHistoryValueInExistingFile() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.folder().get(), ".jhipster/history", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat", Instant.parse("2022-01-24T10:11:12.000Z"));
      generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue);

      // Then
      fileUtils.verify(() -> FileUtils.read(anyString()), times(1));
      fileUtils.verify(() -> {
        ArgumentCaptor<String> fileContentArgCaptor = ArgumentCaptor.forClass(String.class);
        try {
          FileUtils.write(eq(historyFilePath), fileContentArgCaptor.capture(), eq(project.lineEnd()));
        } catch (IOException e) {
          fail("Unexpected IOException");
        }
        List<String> fileContentLines = fileContentArgCaptor.getValue().lines().collect(Collectors.toList());
        assertThat(fileContentLines).isEqualTo(getExpectedFileContentLines());
      });

      verifyNoInteractions(projectRepository);
    }
  }

  @Test
  void shouldAddHistoryValueInNewFile() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.folder().get(), ".jhipster/history", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenThrow(new IOException()).thenReturn(getFileContent());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat", Instant.parse("2022-01-24T10:11:12.000Z"));
      generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue);

      // Then
      fileUtils.verify(() -> FileUtils.read(anyString()), times(2));
      fileUtils.verify(() -> {
        ArgumentCaptor<String> fileContentArgCaptor = ArgumentCaptor.forClass(String.class);
        try {
          FileUtils.write(eq(historyFilePath), fileContentArgCaptor.capture(), eq(project.lineEnd()));
        } catch (IOException e) {
          fail("Unexpected IOException");
        }
        List<String> fileContentLines = fileContentArgCaptor.getValue().lines().collect(Collectors.toList());
        assertThat(fileContentLines).isEqualTo(getExpectedFileContentLines());
      });
    }
  }

  @Test
  void shouldNotAddHistoryValueWhenJsonSerializationError() throws JsonProcessingException {
    // Given
    HistoryProject project = tmpProject();

    ObjectMapper spiedObjectMapper = spy(JsonUtils.getObjectMapper());
    ObjectWriter spiedObjectWriter = spy(spiedObjectMapper.writer());
    when(spiedObjectMapper.writerWithDefaultPrettyPrinter()).thenReturn(spiedObjectWriter);
    when(spiedObjectWriter.writeValueAsString(any())).thenThrow(JsonProcessingException.class);

    try (MockedStatic<JsonUtils> jsonUtils = Mockito.mockStatic(JsonUtils.class)) {
      jsonUtils.when(JsonUtils::getObjectMapper).thenReturn(spiedObjectMapper);

      try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
        String historyFilePath = "/path/history.json";
        fileUtils.when(() -> FileUtils.getPath(project.folder().get(), ".jhipster/history", "history.json")).thenReturn(historyFilePath);
        fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());

        GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat", Instant.parse("2022-01-24T10:11:12.000Z"));

        // When + Then
        assertThatThrownBy(() -> generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue))
          .isExactlyInstanceOf(GeneratorException.class);

        fileUtils.verify(
          () -> {
            try {
              FileUtils.write(anyString(), anyString(), anyString());
            } catch (IOException e) {
              fail("Unexpected IOException");
            }
          },
          never()
        );

        verifyNoInteractions(projectRepository);
      }
    }
  }

  @Test
  void shouldNotAddHistoryValueWheIoExceptionDuringWriting() {
    // Given
    HistoryProject project = tmpProject();

    try (MockedStatic<FileUtils> fileUtils = Mockito.mockStatic(FileUtils.class)) {
      String historyFilePath = "/path/history.json";
      fileUtils.when(() -> FileUtils.getPath(project.folder().get(), ".jhipster/history", "history.json")).thenReturn(historyFilePath);
      fileUtils.when(() -> FileUtils.read(historyFilePath)).thenReturn(getFileContent());
      fileUtils.when(() -> FileUtils.write(anyString(), anyString(), anyString())).thenThrow(new IOException());

      // When
      GeneratorHistoryValue generatorHistoryValue = new GeneratorHistoryValue("tomcat", Instant.parse("2022-01-25T10:11:12.000Z"));
      assertThatThrownBy(() -> generatorHistoryLocalRepository.addHistoryValue(project, generatorHistoryValue))
        .isInstanceOf(GeneratorException.class);
    }
  }

  private String getFileContent() {
    return """
        {
          "values": [
            { "serviceId": "springboot-init"},
            { "serviceId": "java-init", "timestamp": "2022-01-22T10:11:12Z" }
          ]
        }
          """;
  }

  private List<String> getExpectedFileContentLines() {
    return """
        {
          "values" : [ {
            "serviceId" : "springboot-init",
            "timestamp" : null
          }, {
            "serviceId" : "java-init",
            "timestamp" : "2022-01-22T10:11:12Z"
          }, {
            "serviceId" : "tomcat",
            "timestamp" : "2022-01-24T10:11:12Z"
          } ]
        }""".lines()
      .collect(Collectors.toList());
  }
}
