package tech.jhipster.lite.generator.module.domain.replacement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

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
    protected FileReplacer buildReplacer(String file, ElementMatcher toReplace, String replacement) {
      return new MandatoryFileReplacer(file, new MandatoryReplacer(toReplace, replacement));
    }
  }

  private static record MandatoryFileReplacer(String file, MandatoryReplacer replacement) implements FileReplacer {
    public MandatoryFileReplacer {
      Assert.notNull("file", file);
      Assert.notNull("replacement", replacement);
    }

    @Override
    public void apply(JHipsterProjectFolder folder) {
      Path filePath = folder.filePath(file());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement().apply(filePath, content);

        Files.writeString(filePath, updatedContent, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
        throw new ReplacementErrorException(filePath, e);
      }
    }
  }

  private static record MandatoryReplacer(ElementMatcher currentValue, String updatedValue) {
    public MandatoryReplacer {
      Assert.notNull("currentValue", currentValue);
      Assert.notNull("updatedValue", updatedValue);
    }

    public String apply(Path file, String content) {
      if (currentValue().notMatchIn(content)) {
        throw new UnknownCurrentValueException(file, content);
      }

      return currentValue().replacer().apply(content, updatedValue());
    }
  }
}
