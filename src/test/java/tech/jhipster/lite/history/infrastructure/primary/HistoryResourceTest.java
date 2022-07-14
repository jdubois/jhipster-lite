package tech.jhipster.lite.history.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.history.infrastructure.primary.dto.HistoryDTOTest.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.history.application.GeneratorHistoryApplicationService;
import tech.jhipster.lite.history.domain.GeneratorHistoryValue;
import tech.jhipster.lite.history.infrastructure.primary.dto.HistoryDTO;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
@ExtendWith(SpringExtension.class)
class HistoryResourceTest {

  @Mock
  private GeneratorHistoryApplicationService service;

  @InjectMocks
  private HistoryResource resource;

  @Test
  void shouldGetHistoryServiceIdFromService() {
    List<GeneratorHistoryValue> values = values();
    when(service.getValues(any(JHipsterProjectFolder.class))).thenReturn(values);

    ResponseEntity<HistoryDTO> result = resource.serviceIds("/tmp/chips");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(HistoryDTO.from(values));
  }

  @Test
  void shouldGetHistoryFromService() {
    List<GeneratorHistoryValue> values = values();
    when(service.getValues(any(JHipsterProjectFolder.class))).thenReturn(values);

    ResponseEntity<List<GeneratorHistoryValue>> result = resource.history("/tmp/chips");

    assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(result.getBody()).usingRecursiveComparison().isEqualTo(values);
  }
}
