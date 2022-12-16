package tech.jhipster.lite.error_generator.domain;

import tech.jhipster.lite.error.domain.MissingMandatoryValueException;

public final class MissingMandatoryValueFactory {

  public static void throwMissingMandatoryValue() {
    throw missingMandatoryValue();
  }

  public static MissingMandatoryValueException missingMandatoryValue() {
    return MissingMandatoryValueException.forNullValue("myField");
  }
}
