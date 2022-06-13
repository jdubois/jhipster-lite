package tech.jhipster.lite.generator.server.springboot.technicaltools.actuator.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.common.domain.SpringBootCommonService;

@UnitTest
@ExtendWith(MockitoExtension.class)
class SpringBootActuatorDomainServiceTest {

  @Mock
  private BuildToolService buildToolService;

  @Mock
  private SpringBootCommonService springBootCommonService;

  @Spy
  @InjectMocks
  private SpringBootActuatorDomainService springBootActuatorDomainService;

  @Test
  void shouldAddActuator() {
    Map<String, Object> config = new HashMap<>(Map.of("packageName", "beer"));
    Project project = Project.builder().folder("/folder").config(config).build();

    springBootActuatorDomainService.addActuator(project);

    ArgumentCaptor<Dependency> dependencyArgCaptor = ArgumentCaptor.forClass(Dependency.class);
    verify(buildToolService).addDependency(eq(project), dependencyArgCaptor.capture());
    assertThat(dependencyArgCaptor.getValue())
      .extracting(Dependency::getGroupId, Dependency::getArtifactId)
      .containsExactly("org.springframework.boot", "spring-boot-starter-actuator");

    verify(springBootCommonService, times(3)).addProperties(any(Project.class), anyString(), anyString());
    verify(springBootCommonService).addProperties(project, "management.endpoints.web.base-path", "/management");
    verify(springBootCommonService)
      .addProperties(project, "management.endpoints.web.exposure.include", "configprops, env, health, info, logfile, loggers, threaddump");
    verify(springBootCommonService).addProperties(project, "management.endpoint.health.probes.enabled", "true");
  }
}
