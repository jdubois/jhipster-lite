package tech.jhipster.lite.generator.server.springboot.common.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootCommonDomainServiceTest {

  @Mock
  private ProjectRepository projectRepository;

  @InjectMocks
  private SpringBootCommonDomainService springBootCommonDomainService;

  @Test
  void shouldNotCheckIfProjectIsSetWithMariaDbOrMySqlDatabaseWithoutProject() {
    assertThatThrownBy(() -> springBootCommonDomainService.isSetWithMySQLOrMariaDBDatabase(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }

  @Test
  void shouldNotCheckIfProjectIsDatabaseUseSequencesWithoutProject() {
    assertThatThrownBy(() -> springBootCommonDomainService.isDatabaseUseSequences(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("project");
  }
}
