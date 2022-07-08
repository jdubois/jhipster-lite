package tech.jhipster.lite.module.domain.javaproperties;

import java.util.HashMap;
import java.util.Map;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;

public class JHipsterModuleSpringProperties {

  private final Map<PropertyKey, PropertyValue> properties;

  private JHipsterModuleSpringProperties(JHipsterModuleSpringPropertiesBuilder builder) {
    properties = JHipsterCollections.immutable(builder.properties);
  }

  public static JHipsterModuleSpringPropertiesBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleSpringPropertiesBuilder(module);
  }

  public Map<PropertyKey, PropertyValue> properties() {
    return properties;
  }

  public static class JHipsterModuleSpringPropertiesBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<PropertyKey, PropertyValue> properties = new HashMap<>();

    private JHipsterModuleSpringPropertiesBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleSpringPropertiesBuilder set(PropertyKey key, PropertyValue value) {
      Assert.notNull("key", key);
      Assert.notNull("value", value);

      properties.put(key, value);

      return this;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    public JHipsterModuleSpringProperties build() {
      return new JHipsterModuleSpringProperties(this);
    }
  }
}
