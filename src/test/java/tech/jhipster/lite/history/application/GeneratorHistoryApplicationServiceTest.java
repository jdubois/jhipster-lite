package tech.jhipster.lite.history.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.history.domain.GeneratorHistoryData;
import tech.jhipster.lite.history.domain.GeneratorHistoryRepository;
import tech.jhipster.lite.history.domain.HistoryProject;
import tech.jhipster.lite.history.domain.HistoryProjectsFixture;

@UnitTest
@ExtendWith(MockitoExtension.class)
class GeneratorHistoryApplicationServiceTest {

  @InjectMocks
  private GeneratorHistoryApplicationService generatorHistoryApplicationService;

  @Mock
  private GeneratorHistoryRepository generatorHistoryRepository;

  @Test
  void shouldNotGetHistoryWithoutProject() {
    assertThatThrownBy(() -> generatorHistoryApplicationService.getValues(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldGetHistory() {
    HistoryProject project = HistoryProjectsFixture.tmpProject();

    GeneratorHistoryData historyData = new GeneratorHistoryData(new ArrayList<>());
    when(generatorHistoryRepository.getHistoryData(project.folder())).thenReturn(historyData);

    assertThat(generatorHistoryApplicationService.getValues(project.folder())).isEqualTo(historyData.values());
  }
}
