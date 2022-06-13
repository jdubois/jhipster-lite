package tech.jhipster.lite.generator.module.domain.replacement;

import java.nio.file.Path;
import tech.jhipster.lite.error.domain.GeneratorException;

class ReplacementErrorException extends GeneratorException {

  public ReplacementErrorException(Path file, Throwable e) {
    super("Can't apply replacement in " + file.toString() + " (can't update file)", e);
  }
}
