package tech.jhipster.lite.history.infrastructure.secondary;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.common.domain.JsonUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;
import tech.jhipster.lite.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.history.domain.GeneratorHistoryRepository;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.history.domain.HistoryProject;
import tech.jhipster.lite.history.infrastructure.secondary.dto.GeneratorHistoryDataDTO;
import tech.jhipster.lite.history.infrastructure.secondary.dto.GeneratorHistoryValueDTO;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@Repository
public class GeneratorHistoryLocalRepository implements GeneratorHistoryRepository {

  private static final String HISTORY_FILE_NAME = "history.json";
  private static final String HISTORY_FILE_FOLDER_SOURCE = "history";
  private static final String HISTORY_FOLDER_PATH_DEST = ".jhipster/history";

  private final ProjectRepository projectRepository;

  public GeneratorHistoryLocalRepository(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public GeneratorHistoryData getHistoryData(JHipsterProjectFolder folder) {
    String filePath = getHistoryFilePath(folder);
    String historyFileContent;
    try {
      historyFileContent = FileUtils.read(filePath);
    } catch (IOException e) {
      throw new GeneratorException("Error on file " + filePath + " : " + e.getMessage());
    }
    return GeneratorHistoryDataDTO.to(deserializeHistoryFile(filePath, historyFileContent));
  }

  @Override
  public void addHistoryValue(HistoryProject project, GeneratorHistoryValue generatorHistoryValue) {
    String filePath = getHistoryFilePath(project.folder());
    List<GeneratorHistoryValueDTO> generatorHistoryValueDTOs = new ArrayList<>();
    try {
      String historyFileContent = FileUtils.read(filePath);
      generatorHistoryValueDTOs.addAll(deserializeHistoryFile(filePath, historyFileContent));
    } catch (IOException e) {
      // The file does not exist
      createHistoryFile(project);
      GeneratorHistoryData data = getHistoryData(project.folder());
      generatorHistoryValueDTOs.addAll(GeneratorHistoryDataDTO.from(data.values()).values());
    }

    generatorHistoryValueDTOs.add(GeneratorHistoryValueDTO.from(generatorHistoryValue));
    GeneratorHistoryDataDTO generatorHistoryDataDTO = new GeneratorHistoryDataDTO(generatorHistoryValueDTOs);
    String newHistoryFileContent;
    try {
      newHistoryFileContent = JsonUtils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(generatorHistoryDataDTO);
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot serialize the history data " + generatorHistoryDataDTO + " : " + e.getMessage());
    }
    try {
      FileUtils.write(filePath, newHistoryFileContent, project.lineEnd());
    } catch (IOException e) {
      throw new GeneratorException("Cannot write JSON content in file " + filePath + " : " + e.getMessage());
    }
  }

  private void createHistoryFile(HistoryProject project) {
    projectRepository.add(
      ProjectFile
        .forProject(project.toProject())
        .withSource(HISTORY_FILE_FOLDER_SOURCE, HISTORY_FILE_NAME)
        .withDestination(HISTORY_FOLDER_PATH_DEST, HISTORY_FILE_NAME)
    );
  }

  private String getHistoryFilePath(JHipsterProjectFolder folder) {
    return FileUtils.getPath(folder.get(), HISTORY_FOLDER_PATH_DEST, HISTORY_FILE_NAME);
  }

  private List<GeneratorHistoryValueDTO> deserializeHistoryFile(String filePath, String historyFileContent) {
    try {
      return JsonUtils.getObjectMapper().readValue(historyFileContent, GeneratorHistoryDataDTO.class).values();
    } catch (JsonProcessingException e) {
      throw new GeneratorException("Cannot deserialize JSON of file " + filePath + " : " + e.getMessage());
    }
  }
}
