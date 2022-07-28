package tech.jhipster.lite.module.domain.properties;

import java.util.Map;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;

public class JHipsterModuleProperties {

  public static final String BASE_PACKAGE_PROPERTY = "packageName";
  public static final String INDENTATION_PROPERTY = "prettierDefaultIndent";
  public static final String PROJECT_NAME_PROPERTY = "projectName";
  public static final String PROJECT_BASE_NAME_PROPERTY = "baseName";
  public static final String SERVER_PORT_PROPERTY = "serverPort";

  private final JHipsterProjectFolder projectFolder;
  private final boolean commitModule;
  private final Map<String, Object> properties;
  private final Indentation indentation;
  private final JHipsterBasePackage basePackage;
  private final JHipsterProjectName projectName;
  private final JHipsterProjectBaseName projectBaseName;
  private final JHipsterServerPort serverPort;

  public JHipsterModuleProperties(String projectFolder, boolean commitModule, Map<String, Object> properties) {
    this(new JHipsterProjectFolder(projectFolder), commitModule, properties);
  }

  public JHipsterModuleProperties(JHipsterProjectFolder projectFolder, boolean commitModule, Map<String, Object> properties) {
    Assert.notNull("projectFolder", projectFolder);

    this.projectFolder = projectFolder;
    this.commitModule = commitModule;
    this.properties = JHipsterCollections.immutable(properties);

    indentation = Indentation.from(getOrDefault(INDENTATION_PROPERTY, null, Integer.class));
    basePackage = new JHipsterBasePackage(getOrDefault(BASE_PACKAGE_PROPERTY, null, String.class));
    projectName = new JHipsterProjectName(getOrDefault(PROJECT_NAME_PROPERTY, null, String.class));
    projectBaseName = new JHipsterProjectBaseName(getOrDefault(PROJECT_BASE_NAME_PROPERTY, null, String.class));
    serverPort = new JHipsterServerPort(getOrDefault(SERVER_PORT_PROPERTY, null, Integer.class));
  }

  public static JHipsterModuleProperties defaultProperties(JHipsterProjectFolder projectFolder) {
    return new JHipsterModuleProperties(projectFolder, false, null);
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public boolean commitNeeded() {
    return commitModule;
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
    return indentation;
  }

  public String packagePath() {
    return basePackage.path();
  }

  public JHipsterBasePackage basePackage() {
    return basePackage;
  }

  public JHipsterProjectName projectName() {
    return projectName;
  }

  public JHipsterProjectBaseName projectBaseName() {
    return projectBaseName;
  }

  public JHipsterServerPort serverPort() {
    return serverPort;
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

  public Map<String, Object> get() {
    return properties;
  }
}
