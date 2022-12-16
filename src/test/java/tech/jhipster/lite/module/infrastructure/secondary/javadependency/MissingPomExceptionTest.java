package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.ErrorStatus;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class MissingPomExceptionTest {

  @Test
  void shouldGetExceptionInformation() {
    MissingPomException exception = new MissingPomException(new JHipsterProjectFolder("folder"));

    assertThat(exception.getMessage()).isEqualTo("Can't find pom.xml in folder");
    assertThat(exception.status()).isEqualTo(ErrorStatus.BAD_REQUEST);
    assertThat(exception.key()).isEqualTo(JavaDependencyErrorKey.MISSING_POM);
    assertThat(exception.parameters()).containsOnly(entry("folder", "folder"));
  }
}
