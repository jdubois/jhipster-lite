package tech.jhipster.lite.module.domain.file;

import java.util.Collection;
import java.util.stream.Stream;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public record JHipsterFilesToDelete(Collection<JHipsterProjectFilePath> files) {
  public JHipsterFilesToDelete(Collection<JHipsterProjectFilePath> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Stream<JHipsterProjectFilePath> stream() {
    return files().stream();
  }
}
