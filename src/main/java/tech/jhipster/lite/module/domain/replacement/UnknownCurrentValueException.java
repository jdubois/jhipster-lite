package tech.jhipster.lite.module.domain.replacement;

import java.nio.file.Path;
import tech.jhipster.lite.error.domain.GeneratorException;

class UnknownCurrentValueException extends GeneratorException {

  public UnknownCurrentValueException(Path file, String currentValue) {
    super("Can't find \"" + currentValue + "\" in " + file.toString());
  }
}
