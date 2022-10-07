package tech.jhipster.lite.module.domain.replacement;

import java.util.ArrayList;
import java.util.Collection;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.JHipsterProjectFilePath;

public abstract class JHipsterModuleReplacements {

  private final Collection<ContentReplacer> replacements;

  protected JHipsterModuleReplacements(JHipsterModuleReplacementsBuilder<?, ?> builder) {
    Assert.notNull("builder", builder);
    Assert.notNull("replacements", builder.replacements);

    replacements = JHipsterCollections.immutable(builder.replacements);
  }

  public Collection<ContentReplacer> replacements() {
    return replacements;
  }

  public abstract static class JHipsterModuleReplacementsBuilder<
    Replacements extends JHipsterModuleReplacements, FileReplacementsBuilder extends JHipsterModuleFileReplacementsBuilder<?, ?>
  > {

    private final JHipsterModuleBuilder module;
    private final Collection<ContentReplacer> replacements = new ArrayList<>();

    protected JHipsterModuleReplacementsBuilder(JHipsterModuleBuilder module) {
      Assert.notNull("module", module);

      this.module = module;
    }

    public JHipsterModuleBuilder and() {
      return module;
    }

    void add(ContentReplacer fileReplacer) {
      Assert.notNull("fileReplacer", fileReplacer);

      replacements.add(fileReplacer);
    }

    /**
     * @deprecated use {@link #in(JHipsterProjectFilePath file)} instead
     */
    @Deprecated(forRemoval = true)
    @Generated(reason = "Candidate for deletion")
    public FileReplacementsBuilder in(String file) {
      return in(new JHipsterProjectFilePath(file));
    }

    public abstract FileReplacementsBuilder in(JHipsterProjectFilePath file);

    public abstract Replacements build();
  }

  public abstract static class JHipsterModuleFileReplacementsBuilder<
    ReplacementsBuilder extends JHipsterModuleReplacementsBuilder<?, ?>,
    Builder extends JHipsterModuleFileReplacementsBuilder<ReplacementsBuilder, Builder>
  > {

    private final ReplacementsBuilder replacements;
    private final JHipsterProjectFilePath file;

    protected JHipsterModuleFileReplacementsBuilder(ReplacementsBuilder replacements, JHipsterProjectFilePath file) {
      Assert.notNull("replacements", replacements);
      Assert.notNull("file", file);

      this.replacements = replacements;
      this.file = file;
    }

    public Builder add(ElementReplacer elementToReplace, String replacement) {
      Assert.notNull("elementToReplace", elementToReplace);

      replacements.add(buildReplacer(file, elementToReplace, replacement));

      return self();
    }

    @SuppressWarnings("unchecked")
    private Builder self() {
      return (Builder) this;
    }

    public ReplacementsBuilder and() {
      return replacements;
    }

    protected abstract ContentReplacer buildReplacer(JHipsterProjectFilePath file, ElementReplacer toReplace, String replacement);
  }
}
