package tech.jhipster.lite.module.domain;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommands;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacements;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacements;

public class JHipsterModuleChanges {

  private final JHipsterProjectFolder projectFolder;
  private final Indentation indentation;
  private final TemplatedFiles files;
  private final JHipsterModuleMandatoryReplacements mandatoryReplacements;
  private final JHipsterModuleOptionalReplacements optionalReplacements;
  private final JavaBuildCommands javaBuildCommands;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;

  private JHipsterModuleChanges(JHipsterModuleChangesBuilder builder) {
    assertMandatoryFields(builder);

    projectFolder = builder.projectFolder;
    indentation = builder.indentation;
    files = builder.files;
    mandatoryReplacements = builder.mandatoryReplacements;
    optionalReplacements = builder.optionalReplacements;
    javaBuildCommands = builder.javaBuildCommands;
    packageJson = builder.packageJson;
    preActions = builder.preActions;
    postActions = builder.postActions;
    springProperties = builder.springProperties;
  }

  private void assertMandatoryFields(JHipsterModuleChangesBuilder builder) {
    Assert.notNull("projectFolder", builder.projectFolder);
    Assert.notNull("indentation", builder.indentation);
    Assert.notNull("files", builder.files);
    Assert.notNull("mandatoryReplacements", builder.mandatoryReplacements);
    Assert.notNull("optionalReplacements", builder.optionalReplacements);
    Assert.notNull("javaBuildCommands", builder.javaBuildCommands);
    Assert.notNull("preActions", builder.preActions);
    Assert.notNull("postActions", builder.postActions);
    Assert.notNull("springProperties", builder.springProperties);
  }

  public static JHipsterModuleChangesProjectFolderBuilder builder() {
    return new JHipsterModuleChangesBuilder();
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return indentation;
  }

  public TemplatedFiles files() {
    return files;
  }

  public JHipsterModuleMandatoryReplacements mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public JHipsterModuleOptionalReplacements optionalReplacements() {
    return optionalReplacements;
  }

  public JavaBuildCommands javaBuildCommands() {
    return javaBuildCommands;
  }

  public JHipsterModulePackageJson packageJson() {
    return packageJson;
  }

  public JHipsterModulePreActions preActions() {
    return preActions;
  }

  public JHipsterModulePostActions postActions() {
    return postActions;
  }

  public SpringProperties springProperties() {
    return springProperties;
  }

  public static class JHipsterModuleChangesBuilder
    implements
      JHipsterModuleChangesProjectFolderBuilder,
      JHipsterModuleChangesIndentationBuilder,
      JHipsterModuleChangesFilesBuilder,
      JHipsterModuleChangesMandatoryReplacementsBuilder,
      JHipsterModuleChangesOptionalReplacementsBuilder,
      JHipsterModuleChangesJavaBuildCommandsBuilder,
      JHipsterModuleChangesPackageJsonBuilder,
      JHipsterModuleChangesPreActionsBuilder,
      JHipsterModuleChangesPostActionsBuilder,
      JHipsterModuleChangesSpringPropertiesBuilder {

    private JHipsterProjectFolder projectFolder;
    private TemplatedFiles files;
    private JHipsterModuleMandatoryReplacements mandatoryReplacements;
    private JHipsterModuleOptionalReplacements optionalReplacements;
    private JavaBuildCommands javaBuildCommands;
    private JHipsterModulePackageJson packageJson;
    private Indentation indentation;
    private JHipsterModulePreActions preActions;
    private JHipsterModulePostActions postActions;
    private SpringProperties springProperties;

    private JHipsterModuleChangesBuilder() {}

    @Override
    public JHipsterModuleChangesIndentationBuilder projectFolder(JHipsterProjectFolder projectFolder) {
      this.projectFolder = projectFolder;

      return this;
    }

    @Override
    public JHipsterModuleChangesFilesBuilder indentation(Indentation indentation) {
      this.indentation = indentation;

      return this;
    }

    @Override
    public JHipsterModuleChangesMandatoryReplacementsBuilder files(TemplatedFiles files) {
      this.files = files;

      return this;
    }

    @Override
    public JHipsterModuleChangesOptionalReplacementsBuilder mandatoryReplacements(
      JHipsterModuleMandatoryReplacements mandatoryReplacements
    ) {
      this.mandatoryReplacements = mandatoryReplacements;

      return this;
    }

    @Override
    public JHipsterModuleChangesJavaBuildCommandsBuilder optionalReplacements(JHipsterModuleOptionalReplacements optionalReplacements) {
      this.optionalReplacements = optionalReplacements;

      return this;
    }

    @Override
    public JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaDependencies) {
      this.javaBuildCommands = javaDependencies;

      return this;
    }

    @Override
    public JHipsterModuleChangesPreActionsBuilder packageJson(JHipsterModulePackageJson packageJson) {
      this.packageJson = packageJson;

      return this;
    }

    @Override
    public JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions) {
      this.preActions = preActions;

      return this;
    }

    @Override
    public JHipsterModuleChangesSpringPropertiesBuilder postActions(JHipsterModulePostActions postActions) {
      this.postActions = postActions;

      return this;
    }

    @Override
    public JHipsterModuleChanges springProperties(SpringProperties springProperties) {
      this.springProperties = springProperties;

      return new JHipsterModuleChanges(this);
    }
  }

  public interface JHipsterModuleChangesProjectFolderBuilder {
    JHipsterModuleChangesIndentationBuilder projectFolder(JHipsterProjectFolder projectFolder);
  }

  public interface JHipsterModuleChangesIndentationBuilder {
    JHipsterModuleChangesFilesBuilder indentation(Indentation indentation);
  }

  public interface JHipsterModuleChangesFilesBuilder {
    JHipsterModuleChangesMandatoryReplacementsBuilder files(TemplatedFiles files);
  }

  public interface JHipsterModuleChangesMandatoryReplacementsBuilder {
    JHipsterModuleChangesOptionalReplacementsBuilder mandatoryReplacements(JHipsterModuleMandatoryReplacements mandatoryReplacements);
  }

  public interface JHipsterModuleChangesOptionalReplacementsBuilder {
    JHipsterModuleChangesJavaBuildCommandsBuilder optionalReplacements(JHipsterModuleOptionalReplacements optionalReplacements);
  }

  public interface JHipsterModuleChangesJavaBuildCommandsBuilder {
    JHipsterModuleChangesPackageJsonBuilder javaBuildCommands(JavaBuildCommands javaBuildCommands);
  }

  public interface JHipsterModuleChangesPackageJsonBuilder {
    JHipsterModuleChangesPreActionsBuilder packageJson(JHipsterModulePackageJson packageJson);
  }

  public interface JHipsterModuleChangesPreActionsBuilder {
    JHipsterModuleChangesPostActionsBuilder preActions(JHipsterModulePreActions preActions);
  }

  public interface JHipsterModuleChangesPostActionsBuilder {
    JHipsterModuleChangesSpringPropertiesBuilder postActions(JHipsterModulePostActions postActions);
  }

  public interface JHipsterModuleChangesSpringPropertiesBuilder {
    JHipsterModuleChanges springProperties(SpringProperties springProperties);
  }
}
