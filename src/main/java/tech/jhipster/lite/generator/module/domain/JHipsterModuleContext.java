package tech.jhipster.lite.generator.module.domain;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterBasePackage;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

public class JHipsterModuleContext {

  private static final Logger log = LoggerFactory.getLogger(JHipsterModuleContext.class);
  private static final String IDENTATION_KEY = "prettierDefaultIndent";

  private final Map<String, Object> context;
  private final Indentation indentation;

  private JHipsterModuleContext(JHipsterModuleContextBuilder builder) {
    context = JHipsterCollections.immutable(builder.context);
    indentation = loadIndentation();
  }

  private Indentation loadIndentation() {
    Object contextIndentation = context.get(IDENTATION_KEY);

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

      init.put("baseName", properties.projectBaseName().get());
      init.put("projectName", properties.projectName().get());
      init.put("packageName", properties.basePackage().get());
      init.put(IDENTATION_KEY, properties.indentation().spacesCount());

      return init;
    }

    public JHipsterModuleContextBuilder packageName(JHipsterBasePackage basePackage) {
      Assert.notNull("basePackage", basePackage);

      return put("packageName", basePackage.get());
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
