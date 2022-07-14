package tech.jhipster.lite.module.domain;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class JHipsterModuleContext {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModuleContext.class);

  private final Map<String, Object> context;
  private final Indentation indentation;

  private JHipsterModuleContext(JHipsterModuleContextBuilder builder) {
    context = JHipsterCollections.immutable(builder.context);
    indentation = loadIndentation();
  }

  private Indentation loadIndentation() {
    Object contextIndentation = context.get(JHipsterModuleProperties.INDENTATION_PROPERTY);

    if (contextIndentation instanceof Integer integerIndentation) {
      return Indentation.from(integerIndentation);
    }

    log.info("Context contains an invalid indentation, using default");
    return Indentation.DEFAULT;
  }

  static JHipsterModuleContextBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleContextBuilder(module);
  }

  public Map<String, Object> get() {
    return context;
  }

  public Indentation indentation() {
    return indentation;
  }

  public static class JHipsterModuleContextBuilder {

    private final JHipsterModuleBuilder module;
    private final Map<String, Object> context;

    private JHipsterModuleContextBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
      context = initialContext(module.properties());
    }

    private Map<String, Object> initialContext(JHipsterModuleProperties properties) {
      HashMap<String, Object> init = new HashMap<>();

      init.put(JHipsterModuleProperties.PROJECT_BASE_NAME_PROPERTY, properties.projectBaseName().get());
      init.put(JHipsterModuleProperties.PROJECT_NAME_PROPERTY, properties.projectName().get());
      init.put(JHipsterModuleProperties.BASE_PACKAGE_PROPERTY, properties.basePackage().get());
      init.put(JHipsterModuleProperties.SERVER_PORT_PROPERTY, properties.serverPort().get());
      init.put(JHipsterModuleProperties.INDENTATION_PROPERTY, properties.indentation().spacesCount());

      return init;
    }

    public JHipsterModuleContextBuilder put(String key, Object value) {
      Assert.notBlank("key", key);
      Assert.notNull("value", value);

      context.put(key, value);

      return this;
    }

    public JHipsterModuleContext build() {
      return new JHipsterModuleContext(this);
    }

    public JHipsterModuleBuilder and() {
      return module;
    }
  }
}
