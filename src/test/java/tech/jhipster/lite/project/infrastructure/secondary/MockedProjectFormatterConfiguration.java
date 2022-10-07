package tech.jhipster.lite.project.infrastructure.secondary;

import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class MockedProjectFormatterConfiguration {

  @Bean
  @Primary
  ProjectFormatter projectFormatter() {
    return mock(ProjectFormatter.class);
  }
}
