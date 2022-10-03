package tech.jhipster.lite.module.infrastructure.secondary.javadependency;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.javadependency.JavaDependenciesVersions;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;

@UnitTest
class JHipsterJavaDependenciesVersionsRepositoryTest {

  @Test
  void shouldGetVersionsFromReaders() {
    JavaDependenciesReader customVersions = () -> versions(version("jjwt", "1.2.3"));
    JavaDependenciesReader defaultVersions = () -> versions(version("jjwt", "1.1.3"), version("spring", "2.1.2"));

    JHipsterJavaDependenciesVersionsRepository repository = new JHipsterJavaDependenciesVersionsRepository(
      List.of(customVersions, defaultVersions)
    );

    assertThat(repository.get()).usingRecursiveComparison().isEqualTo(versions(version("jjwt", "1.2.3"), version("spring", "2.1.2")));
  }

  private static JavaDependenciesVersions versions(JavaDependencyVersion... versions) {
    return new JavaDependenciesVersions(List.of(versions));
  }

  private static JavaDependencyVersion version(String slug, String version) {
    return new JavaDependencyVersion(slug, version);
  }
}
