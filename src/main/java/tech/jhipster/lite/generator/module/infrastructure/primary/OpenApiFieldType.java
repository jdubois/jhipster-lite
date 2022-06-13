package tech.jhipster.lite.generator.module.infrastructure.primary;

enum OpenApiFieldType {
  STRING("string"),
  INTEGER("integer"),
  BOOLEAN("boolean");

  private final String key;

  OpenApiFieldType(String key) {
    this.key = key;
  }

  public String key() {
    return key;
  }
}
