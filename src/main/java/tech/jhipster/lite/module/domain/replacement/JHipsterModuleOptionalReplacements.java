package tech.jhipster.lite.module.domain.replacement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;

public class JHipsterModuleOptionalReplacements extends JHipsterModuleReplacements {

  private JHipsterModuleOptionalReplacements(JHipsterModuleOptionalReplacementsBuilder builder) {
    super(builder);
  }

  public static JHipsterModuleOptionalReplacementsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleOptionalReplacementsBuilder(module);
  }

  public static class JHipsterModuleOptionalReplacementsBuilder
    extends JHipsterModuleReplacementsBuilder<JHipsterModuleOptionalReplacements, JHipsterModuleFileOptionalReplacementsBuilder> {

    private JHipsterModuleOptionalReplacementsBuilder(JHipsterModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileOptionalReplacementsBuilder in(String file) {
      return new JHipsterModuleFileOptionalReplacementsBuilder(this, file);
    }

    @Override
    public JHipsterModuleOptionalReplacements build() {
      return new JHipsterModuleOptionalReplacements(this);
    }
  }

  public static class JHipsterModuleFileOptionalReplacementsBuilder
    extends JHipsterModuleFileReplacementsBuilder<JHipsterModuleOptionalReplacementsBuilder, JHipsterModuleFileOptionalReplacementsBuilder> {

    private JHipsterModuleFileOptionalReplacementsBuilder(JHipsterModuleOptionalReplacementsBuilder replacements, String file) {
      super(replacements, file);
    }

    @Override
    protected ContentReplacement buildReplacer(String file, ElementReplacer toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }

  private static record OptionalFileReplacer(String file, OptionalReplacer replacement) implements ContentReplacement {
    private static final Logger log = LoggerFactory.getLogger(OptionalFileReplacer.class);

    public OptionalFileReplacer {
      Assert.notNull("file", file);
      Assert.notNull("replacement", replacement);
    }

    @Override
    public String apply(String content) {
      return replacement().apply(content);
    }

    @Override
    public void handleError(Throwable e) {
      log.debug("Can't apply optional replacement: {}", e.getMessage());
    }
  }

  private static record OptionalReplacer(ElementReplacer currentValue, String updatedValue) {
    public OptionalReplacer {
      Assert.notNull("currentValue", currentValue);
      Assert.notNull("updatedValue", updatedValue);
    }

    public String apply(String content) {
      return currentValue().replacer().apply(content, updatedValue());
    }
  }
}
