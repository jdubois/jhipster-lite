package tech.jhipster.lite.generator.module.domain;

import java.util.Collection;
import tech.jhipster.lite.common.domain.JHipsterCollections;

public record TemplatedFiles(Collection<TemplatedFile> files) {
  public TemplatedFiles(Collection<TemplatedFile> files) {
    this.files = JHipsterCollections.immutable(files);
  }

  public Collection<TemplatedFile> get() {
    return files();
  }
}
