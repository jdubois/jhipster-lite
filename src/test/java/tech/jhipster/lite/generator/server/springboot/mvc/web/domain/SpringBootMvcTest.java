package tech.jhipster.lite.generator.server.springboot.mvc.web.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

@UnitTest
class SpringBootMvcTest {

  @Test
  void shouldSpringBootStarterWebDependency() {
    Dependency dependency = SpringBootMvc.springBootStarterWebDependency();

    assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
    assertThat(dependency.getArtifactId()).isEqualTo("spring-boot-starter-web");
    assertThat(dependency.getVersion()).isEmpty();
    assertThat(dependency.getScope()).isEmpty();
  }

  @Test
  void shouldTomcatDependency() {
    Dependency dependency = SpringBootMvc.tomcatDependency();

    assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
    assertThat(dependency.getArtifactId()).isEqualTo("spring-boot-starter-tomcat");
    assertThat(dependency.getVersion()).isEmpty();
    assertThat(dependency.getScope()).isEmpty();
  }

  @Test
  void shouldUndertowDependency() {
    Dependency dependency = SpringBootMvc.undertowDependency();

    assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
    assertThat(dependency.getArtifactId()).isEqualTo("spring-boot-starter-undertow");
    assertThat(dependency.getVersion()).isEmpty();
    assertThat(dependency.getScope()).isEmpty();
  }

  @Test
  void shouldProblemSpringDependency() {
    Dependency dependency = SpringBootMvc.problemSpringDependency();

    assertThat(dependency.getGroupId()).isEqualTo("org.zalando");
    assertThat(dependency.getArtifactId()).isEqualTo("problem-spring-web");
    assertThat(dependency.getVersion()).contains("\\${problem-spring-web.version}");
    assertThat(dependency.getScope()).isEmpty();
  }

  @Test
  void shouldSpringBootStarterValidation() {
    Dependency dependency = SpringBootMvc.springBootStarterValidation();

    assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
    assertThat(dependency.getArtifactId()).isEqualTo("spring-boot-starter-validation");
    assertThat(dependency.getVersion()).isEmpty();
    assertThat(dependency.getScope()).isEmpty();
  }

  @Test
  void shouldCorsFiles() {
    List<String> corsFiles = List.of("CorsFilterConfiguration.java", "CorsProperties.java");

    assertThat(SpringBootMvc.corsFiles().keySet()).containsExactlyInAnyOrderElementsOf(corsFiles);
  }
}
