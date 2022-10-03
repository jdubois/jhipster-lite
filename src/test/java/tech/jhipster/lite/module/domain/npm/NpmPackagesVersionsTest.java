package tech.jhipster.lite.module.domain.npm;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class NpmPackagesVersionsTest {

  @Test
  void shouldNotVersionFromUnkownSource() {
    NpmPackagesVersions versions = NpmPackagesVersions.builder().build();

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), NpmVersionSource.COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("COMMON");
  }

  @Test
  void shouldNotGetUnknownPackageVersion() {
    NpmPackagesVersions versions = NpmPackagesVersions
      .builder()
      .put(NpmVersionSource.COMMON, List.of(new NpmPackage("vue", "1.2.3")))
      .build();

    assertThatThrownBy(() -> versions.get(new NpmPackageName("unknown"), NpmVersionSource.COMMON))
      .isExactlyInstanceOf(UnknownNpmPackageException.class)
      .hasMessageContaining("unknown")
      .hasMessageContaining("COMMON");
  }

  @Test
  void shouldGetVersion() {
    NpmPackagesVersions versions = NpmPackagesVersions
      .builder()
      .put(NpmVersionSource.COMMON, List.of(new NpmPackage("vue", "1.2.3")))
      .put(NpmVersionSource.VUE, List.of(new NpmPackage("vue", "1.2.4")))
      .build();

    assertThat(versions.get(new NpmPackageName("vue"), NpmVersionSource.VUE)).isEqualTo(new NpmPackageVersion("1.2.4"));
    assertThat(versions.get(new NpmPackageName("vue"), NpmVersionSource.COMMON)).isEqualTo(new NpmPackageVersion("1.2.3"));
  }
}
