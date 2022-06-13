package tech.jhipster.lite.generator.server.springboot.common.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

@UnitTest
class SpringBootCommonTest {

  @Test
  void shouldNotGetTestContainersDependencyWithoutDatabase() {
    assertThatThrownBy(() -> SpringBootCommon.testContainersDependency(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("artifactId");
  }

  @Test
  void shouldNotGetTestContainersDependencyWithEmptyDatabase() {
    assertThatThrownBy(() -> SpringBootCommon.testContainersDependency(""))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("artifactId");
  }

  @Test
  void shouldGetTestContainersDependencyDependency() {
    Dependency dependency = SpringBootCommon.testContainersDependency("anyDB");

    assertThat(dependency.getArtifactId()).isEqualTo("anyDB");
    assertThat(dependency.getGroupId()).isEqualTo("org.testcontainers");
    assertThat(dependency.getScope()).contains("test");
  }
}
