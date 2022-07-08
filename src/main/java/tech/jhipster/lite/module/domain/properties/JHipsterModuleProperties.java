package tech.jhipster.lite.module.domain.properties;

import java.util.Map;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;

public record JHipsterModuleProperties(JHipsterProjectFolder projectFolder, Map<String, Object> properties) {
  public static final String BASE_PACKAGE_PROPERTY = "packageName";
  public static final String INDENTATION_PROPERTY = "prettierDefaultIndent";
  public static final String PROJECT_NAME_PROPERTY = "projectName";
  public static final String PROJECT_BASE_NAME_PROPERTY = "baseName";

  public JHipsterModuleProperties(String projectFolder, Map<String, Object> properties) {
    this(new JHipsterProjectFolder(projectFolder), properties);
  }

  public JHipsterModuleProperties(JHipsterProjectFolder projectFolder, Map<String, Object> properties) {
    Assert.notNull("projectFolder", projectFolder);

    this.projectFolder = projectFolder;
    this.properties = JHipsterCollections.immutable(properties);
  }

  public static JHipsterModuleProperties defaultProperties(JHipsterProjectFolder projectFolder) {
    return new JHipsterModuleProperties(projectFolder, null);
  }

  public static JHipsterModuleProperties defaultProperties(String projectFolder) {
    return new JHipsterModuleProperties(projectFolder, null);
  }

  public String getString(String key) {
    return get(key, String.class);
  }

  public String getOrDefaultString(String key, String defaultValue) {
    Assert.notBlank("defaultValue", defaultValue);

    return getOrDefault(key, defaultValue, String.class);
  }

  public boolean getBoolean(String key) {
    return get(key, Boolean.class);
  }

  public boolean getOrDefaultBoolean(String key, boolean defaultValue) {
    return getOrDefault(key, defaultValue, Boolean.class);
  }

  public int getInteger(String key) {
    return get(key, Integer.class);
  }

  public int getOrDefaultInteger(String key, int defaultValue) {
    return getOrDefault(key, defaultValue, Integer.class);
  }

  public Indentation indentation() {
    return Indentation.from(getOrDefault(INDENTATION_PROPERTY, null, Integer.class));
  }

  public JHipsterBasePackage basePackage() {
    return new JHipsterBasePackage(getOrDefault(BASE_PACKAGE_PROPERTY, null, String.class));
  }

  public JHipsterProjectName projectName() {
    return new JHipsterProjectName(getOrDefault(PROJECT_NAME_PROPERTY, null, String.class));
  }

  public JHipsterProjectBaseName projectBaseName() {
    return new JHipsterProjectBaseName(getOrDefault(PROJECT_BASE_NAME_PROPERTY, null, String.class));
  }

  private <T> T getOrDefault(String key, T defaultValue, Class<T> clazz) {
    Assert.notBlank("key", key);

    if (!properties.containsKey(key)) {
      return defaultValue;
    }

    return get(key, clazz);
  }

  private <T> T get(String key, Class<T> clazz) {
    Assert.notBlank("key", key);

    Object property = properties.get(key);

    if (property == null) {
      throw new UnknownPropertyException(key);
    }

    if (clazz.isInstance(property)) {
      return clazz.cast(property);
    }

    throw InvalidPropertyTypeException.builder().key(key).expectedType(clazz).actualType(property.getClass());
  }
}
