package tech.jhipster.lite.generator.module.domain.replacement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

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
    protected FileReplacer buildReplacer(String file, ElementMatcher toReplace, String replacement) {
      return new OptionalFileReplacer(file, new OptionalReplacer(toReplace, replacement));
    }
  }

  private static record OptionalFileReplacer(String file, OptionalReplacer replacement) implements FileReplacer {
    private static final Logger log = LoggerFactory.getLogger(FileReplacer.class);

    public OptionalFileReplacer {
      Assert.notNull("file", file);
      Assert.notNull("replacement", replacement);
    }

    @Override
    public void apply(JHipsterProjectFolder folder) {
      Path filePath = folder.filePath(file());

      try {
        String content = Files.readString(filePath);
        String updatedContent = replacement().apply(content);

        Files.writeString(filePath, updatedContent, StandardOpenOption.TRUNCATE_EXISTING);
      } catch (IOException e) {
        log.debug("Can't replace content, no replacement done {}", e.getMessage(), e);
      }
    }
  }

  private static record OptionalReplacer(ElementMatcher currentValue, String updatedValue) {
    public OptionalReplacer {
      Assert.notNull("currentValue", currentValue);
      Assert.notNull("updatedValue", updatedValue);
    }

    public String apply(String content) {
      return currentValue().replacer().apply(content, updatedValue());
    }
  }
}
