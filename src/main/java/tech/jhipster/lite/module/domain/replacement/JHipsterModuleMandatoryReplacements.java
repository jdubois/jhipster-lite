package tech.jhipster.lite.module.domain.replacement;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;

public class JHipsterModuleMandatoryReplacements extends JHipsterModuleReplacements {

  private JHipsterModuleMandatoryReplacements(JHipsterModuleMandatoryReplacementsBuilder builder) {
    super(builder);
  }

  public static JHipsterModuleMandatoryReplacementsBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleMandatoryReplacementsBuilder(module);
  }

  public static class JHipsterModuleMandatoryReplacementsBuilder
    extends JHipsterModuleReplacementsBuilder<JHipsterModuleMandatoryReplacements, JHipsterModuleFileMandatoryReplacementsBuilder> {

    private JHipsterModuleMandatoryReplacementsBuilder(JHipsterModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileMandatoryReplacementsBuilder in(String file) {
      return new JHipsterModuleFileMandatoryReplacementsBuilder(this, file);
    }

    @Override
    public JHipsterModuleMandatoryReplacements build() {
      return new JHipsterModuleMandatoryReplacements(this);
    }
  }

  public static class JHipsterModuleFileMandatoryReplacementsBuilder
    extends JHipsterModuleFileReplacementsBuilder<JHipsterModuleMandatoryReplacementsBuilder, JHipsterModuleFileMandatoryReplacementsBuilder> {

    private JHipsterModuleFileMandatoryReplacementsBuilder(JHipsterModuleMandatoryReplacementsBuilder replacements, String file) {
      super(replacements, file);
    }

    @Override
    protected ContentReplacer buildReplacer(String file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }

  private static record MandatoryFileReplacer(String file, MandatoryReplacer replacement) implements ContentReplacer {
    public MandatoryFileReplacer {
      Assert.notNull("file", file);
      Assert.notNull("replacement", replacement);
    }

    @Override
    public String apply(String content) {
      return replacement().apply(content);
    }

    @Override
    public void handleError(Throwable e) {
      throw new MandatoryReplacementException(e);
    }
  }

  private static record MandatoryReplacer(ElementReplacer replacer, String updatedValue) {
    public MandatoryReplacer {
      Assert.notNull("replacer", replacer);
      Assert.notNull("updatedValue", updatedValue);
    }

    public String apply(String content) {
      if (replacer.dontNeedReplacement(content, updatedValue())) {
        return content;
      }

      if (replacer().notMatchIn(content)) {
        throw new UnknownCurrentValueException(replacer().searchMatcher(), content);
      }

      return replacer().replacement().apply(content, updatedValue());
    }
  }
}
