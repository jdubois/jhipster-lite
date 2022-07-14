package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.GeneratorException;

class UnknownCurrentValueException extends GeneratorException {

  public UnknownCurrentValueException(String currentValue, String content) {
    super("Can't find \"" + currentValue + "\" in " + content);
  }
}
