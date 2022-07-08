package tech.jhipster.lite.module.domain.replacement;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

abstract class JHipsterModuleReplacements {

  private final Collection<FileReplacer> replacers;

  protected JHipsterModuleReplacements(JHipsterModuleReplacementsBuilder<?, ?> builder) {
    Assert.notNull("builder", builder);
    Assert.notNull("builder", builder.replacers);

    replacers = builder.replacers;
  }

  public void apply(JHipsterProjectFolder folder) {
    Assert.notNull("folder", folder);

    replacers.forEach(replacement -> replacement.apply(folder));
  }

  public abstract static class JHipsterModuleReplacementsBuilder<
    Replacements extends JHipsterModuleReplacements, FileReplacementsBuilder extends JHipsterModuleFileReplacementsBuilder<?, ?>
  > {

    private final JHipsterModuleBuilder module;
    private final Collection<FileReplacer> replacers = new ArrayList<>();

    protected JHipsterModuleReplacementsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    void add(FileReplacer fileReplacer) {
      Assert.notNull("fileReplacer", fileReplacer);

      replacers.add(fileReplacer);
    }

    public abstract FileReplacementsBuilder in(String file);

    public abstract Replacements build();
  }

  public abstract static class JHipsterModuleFileReplacementsBuilder<
    ReplacementsBuilder extends JHipsterModuleReplacementsBuilder<?, ?>,
    Builder extends JHipsterModuleFileReplacementsBuilder<ReplacementsBuilder, Builder>
  > {

    private final ReplacementsBuilder replacements;
    private final String file;

    protected JHipsterModuleFileReplacementsBuilder(ReplacementsBuilder replacements, String file) {
      Assert.notNull("replacements", replacements);
      Assert.notBlank("file", file);

      this.replacements = replacements;
      this.file = file;
    }

    public Builder add(ElementMatcher elementToReplace, String replacement) {
      Assert.notNull("elementToReplace", elementToReplace);

      replacements.add(buildReplacer(file, elementToReplace, replacement));

      return self();
    }

    public Builder add(PositionalMatcher positional, String replacement) {
      Assert.notNull("PositionalMatcher", positional);

      replacements.add(buildReplacer(file, positional.element(), positional.buildReplacement(replacement)));

      return self();
    }

    @SuppressWarnings("unchecked")
    private Builder self() {
      return (Builder) this;
    }

    public ReplacementsBuilder and() {
      return replacements;
    }

    protected abstract FileReplacer buildReplacer(String file, ElementMatcher toReplace, String replacement);
  }
}
