package tech.jhipster.lite.module.domain.replacement;

import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public class JHipsterModuleMandatoryReplacementsFactory extends JHipsterModuleReplacementsFactory {

  private JHipsterModuleMandatoryReplacementsFactory(JHipsterModuleMandatoryReplacementsFactoryBuilder builder) {
    super(builder);
  }

  public static JHipsterModuleMandatoryReplacementsFactoryBuilder builder(JHipsterModuleBuilder module) {
    return new JHipsterModuleMandatoryReplacementsFactoryBuilder(module);
  }

  public Stream<ContentReplacer> replacers() {
    return getReplacers().stream();
  }

  public static class JHipsterModuleMandatoryReplacementsFactoryBuilder
    extends JHipsterModuleReplacementsFactoryBuilder<JHipsterModuleMandatoryReplacementsFactory, JHipsterModuleFileMandatoryReplacementsFactoryBuilder> {

    private JHipsterModuleMandatoryReplacementsFactoryBuilder(JHipsterModuleBuilder module) {
      super(module);
    }

    @Override
    public JHipsterModuleFileMandatoryReplacementsFactoryBuilder in(JHipsterProjectFilePath file) {
      return new JHipsterModuleFileMandatoryReplacementsFactoryBuilder(this, file);
    }

    @Override
    public JHipsterModuleMandatoryReplacementsFactory build() {
      return new JHipsterModuleMandatoryReplacementsFactory(this);
    }
  }

  public static class JHipsterModuleFileMandatoryReplacementsFactoryBuilder
    extends JHipsterModuleFileReplacementsBuilder<JHipsterModuleMandatoryReplacementsFactoryBuilder, JHipsterModuleFileMandatoryReplacementsFactoryBuilder> {

    private JHipsterModuleFileMandatoryReplacementsFactoryBuilder(
      JHipsterModuleMandatoryReplacementsFactoryBuilder replacements,
      JHipsterProjectFilePath file
    ) {
      super(replacements, file);
    }

    @Override
    protected ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }

  private record MandatoryFileReplacer(JHipsterProjectFilePath file, MandatoryReplacer replacement) implements ContentReplacer {
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

  private record MandatoryReplacer(ElementReplacer replacer, String updatedValue) {
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
