package tech.jhipster.lite.module.infrastructure.secondary.npm;

import java.util.Collection;
import java.util.function.Supplier;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.common.domain.Memoizers;
import tech.jhipster.lite.module.domain.npm.NpmPackagesVersions;
import tech.jhipster.lite.module.domain.npm.NpmVersions;

@Repository
class JHipsterNpmVersions implements NpmVersions {

  private final Supplier<NpmPackagesVersions> versions;

  public JHipsterNpmVersions(Collection<NpmVersionsReader> readers) {
    versions = Memoizers.of(versionsReader(readers));
  }

  private Supplier<NpmPackagesVersions> versionsReader(Collection<NpmVersionsReader> readers) {
    return () -> readers.stream().map(NpmVersionsReader::get).reduce(NpmPackagesVersions.EMPTY, NpmPackagesVersions::merge);
  }

  @Override
  public NpmPackagesVersions get() {
    return versions.get();
  }
}
