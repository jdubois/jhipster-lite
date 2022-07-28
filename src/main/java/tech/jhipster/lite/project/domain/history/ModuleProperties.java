package tech.jhipster.lite.project.domain.history;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;

public record ModuleProperties(Map<String, Object> properties) {
  public static final ModuleProperties EMPTY = new ModuleProperties(Map.of());

  public ModuleProperties(Map<String, Object> properties) {
    this.properties = JHipsterCollections.immutable(properties);
  }

  public Map<String, Object> get() {
    return properties();
  }

  public ModuleProperties merge(ModuleProperties other) {
    Assert.notNull("other", other);

    Map<String, Object> mergedProperties = new HashMap<>();
    mergedProperties.putAll(properties);
    mergedProperties.putAll(other.properties);

    return new ModuleProperties(mergedProperties);
  }
}
