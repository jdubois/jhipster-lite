package tech.jhipster.lite.module.domain;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.module.domain.JHipsterModuleFiles.JHipsterModuleFilesBuilder;
import tech.jhipster.lite.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JHipsterModuleJavaBuildPlugin.JHipsterModuleJavaBuildPluginBuilder;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin;
import tech.jhipster.lite.module.domain.javabuildplugin.JavaBuildPlugin.JavaBuildPluginGroupIdBuilder;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies;
import tech.jhipster.lite.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.JHipsterModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import tech.jhipster.lite.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.module.domain.javaproperties.SpringProfile;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.module.domain.javaproperties.SpringPropertyType;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson;
import tech.jhipster.lite.module.domain.packagejson.JHipsterModulePackageJson.JHipsterModulePackageJsonBuilder;
import tech.jhipster.lite.module.domain.packagejson.PackageName;
import tech.jhipster.lite.module.domain.packagejson.ScriptCommand;
import tech.jhipster.lite.module.domain.packagejson.ScriptKey;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.module.domain.postaction.JHipsterModulePostActions.JHipsterModulePostActionsBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.module.domain.replacement.ElementMatcher;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacements;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleMandatoryReplacements.JHipsterModuleMandatoryReplacementsBuilder;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacements;
import tech.jhipster.lite.module.domain.replacement.JHipsterModuleOptionalReplacements.JHipsterModuleOptionalReplacementsBuilder;
import tech.jhipster.lite.module.domain.replacement.JustAfter;
import tech.jhipster.lite.module.domain.replacement.JustBefore;
import tech.jhipster.lite.module.domain.replacement.JustLineAfter;
import tech.jhipster.lite.module.domain.replacement.JustLineBefore;
import tech.jhipster.lite.module.domain.replacement.RegexMatcher;
import tech.jhipster.lite.module.domain.replacement.TextMatcher;

public class JHipsterModule {

  public static final String LINE_BREAK = "\n";

  private final JHipsterProjectFolder projectFolder;
  private final Collection<JHipsterModuleFile> files;
  private final JHipsterModuleMandatoryReplacements mandatoryReplacements;
  private final JHipsterModuleOptionalReplacements optionalReplacements;
  private final JHipsterModuleContext context;
  private final JHipsterModuleJavaDependencies javaDependencies;
  private final JHipsterModuleJavaBuildPlugin javaBuildPlugins;
  private final JHipsterModulePackageJson packageJson;
  private final JHipsterModulePreActions preActions;
  private final JHipsterModulePostActions postActions;
  private final SpringProperties springProperties;

  private JHipsterModule(JHipsterModuleBuilder builder) {
    projectFolder = builder.projectFolder;

    files = builder.files.build().get();
    mandatoryReplacements = builder.mandatoryReplacements.build();
    optionalReplacements = builder.optionalReplacements.build();
    context = builder.context.build();
    javaDependencies = builder.javaDependencies.build();
    javaBuildPlugins = builder.javaBuildPlugins.build();
    packageJson = builder.packageJson.build();
    preActions = builder.preActions.build();
    postActions = builder.postActions.build();
    springProperties = buildSpringProperties(builder);
  }

  private SpringProperties buildSpringProperties(JHipsterModuleBuilder builder) {
    List<SpringProperty> properties = builder.springProperties.entrySet().stream().flatMap(toProperties()).toList();

    return new SpringProperties(properties);
  }

  private Function<Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder>, Stream<SpringProperty>> toProperties() {
    return properties -> properties.getValue().build().properties().entrySet().stream().map(toProperty(properties));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringProperty> toProperty(
    Entry<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> properties
  ) {
    return property ->
      SpringProperty
        .builder(properties.getKey().type())
        .key(property.getKey())
        .value(property.getValue())
        .profile(properties.getKey().profile())
        .build();
  }

  public static JHipsterModuleBuilder moduleBuilder(JHipsterModuleProperties properties) {
    return new JHipsterModuleBuilder(properties);
  }

  public static JavaDependencyGroupIdBuilder javaDependency() {
    return JavaDependency.builder();
  }

  public static JavaBuildPluginGroupIdBuilder javaBuildPlugin() {
    return JavaBuildPlugin.builder();
  }

  public static JHipsterSource from(String source) {
    Assert.notBlank("source", source);

    return new JHipsterSource(Paths.get("/generator", source));
  }

  public static JHipsterDestination to(String destination) {
    return new JHipsterDestination(destination);
  }

  public static JHipsterDestination toSrcMainJava() {
    return JHipsterDestination.SRC_MAIN_JAVA;
  }

  public static JHipsterDestination toSrcMainDocker() {
    return JHipsterDestination.SRC_MAIN_DOCKER;
  }

  public static JHipsterDestination toSrcTestJava() {
    return JHipsterDestination.SRC_TEST_JAVA;
  }

  public static GroupId groupId(String groupId) {
    return new GroupId(groupId);
  }

  public static ArtifactId artifactId(String artifactId) {
    return new ArtifactId(artifactId);
  }

  public static VersionSlug versionSlug(String versionSlug) {
    return new VersionSlug(versionSlug);
  }

  public static TextMatcher text(String text) {
    return new TextMatcher(text);
  }

  public static RegexMatcher regex(String regex) {
    return new RegexMatcher(regex);
  }

  public static JustBefore justBefore(ElementMatcher matcher) {
    return new JustBefore(matcher);
  }

  public static JustAfter justAfter(ElementMatcher matcher) {
    return new JustAfter(matcher);
  }

  public static JustLineBefore justLineBefore(ElementMatcher matcher) {
    return new JustLineBefore(matcher);
  }

  public static JustLineAfter justLineAfter(ElementMatcher matcher) {
    return new JustLineAfter(matcher);
  }

  public static PropertyKey propertyKey(String key) {
    return new PropertyKey(key);
  }

  public static PropertyValue propertyValue(String... values) {
    return new PropertyValue(values);
  }

  public static SpringProfile springProfile(String profile) {
    return new SpringProfile(profile);
  }

  public static DocumentationTitle documentationTitle(String title) {
    return new DocumentationTitle(title);
  }

  public static ScriptKey scriptKey(String key) {
    return new ScriptKey(key);
  }

  public static ScriptCommand scriptCommand(String command) {
    return new ScriptCommand(command);
  }

  public static PackageName packageName(String packageName) {
    return new PackageName(packageName);
  }

  public JHipsterProjectFolder projectFolder() {
    return projectFolder;
  }

  public Indentation indentation() {
    return context.indentation();
  }

  public TemplatedFiles templatedFiles() {
    List<TemplatedFile> templatedFiles = files.stream().map(file -> TemplatedFile.builder().file(file).context(context).build()).toList();

    return new TemplatedFiles(templatedFiles);
  }

  public JHipsterModuleMandatoryReplacements mandatoryReplacements() {
    return mandatoryReplacements;
  }

  public JHipsterModuleOptionalReplacements optionalReplacements() {
    return optionalReplacements;
  }

  public JHipsterModuleJavaDependencies javaDependencies() {
    return javaDependencies;
  }

  public JHipsterModuleJavaBuildPlugin javaBuildPlugins() {
    return javaBuildPlugins;
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

  public static class JHipsterModuleBuilder {

    private static final String PROFILE = "profile";
    private static final String README = "README.md";
    private static final String JHIPSTER_DOCUMENTATION_NEEDLE = "<!-- jhipster-needle-documentation -->";
    private static final String JHIPSTER_README_SECTION_NEEDLE = "<!-- jhipster-needle-readme -->";

    private final JHipsterProjectFolder projectFolder;
    private final JHipsterModuleProperties properties;
    private final JHipsterModuleContextBuilder context;
    private final JHipsterModuleFilesBuilder files = JHipsterModuleFiles.builder(this);
    private final JHipsterModuleMandatoryReplacementsBuilder mandatoryReplacements = JHipsterModuleMandatoryReplacements.builder(this);
    private final JHipsterModuleOptionalReplacementsBuilder optionalReplacements = JHipsterModuleOptionalReplacements.builder(this);
    private final JHipsterModuleJavaDependenciesBuilder javaDependencies = JHipsterModuleJavaDependencies.builder(this);
    private final JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins = JHipsterModuleJavaBuildPlugin.builder(this);
    private final JHipsterModulePackageJsonBuilder packageJson = JHipsterModulePackageJson.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final JHipsterModulePostActionsBuilder postActions = JHipsterModulePostActions.builder(this);
    private final Map<PropertiesKey, JHipsterModuleSpringPropertiesBuilder> springProperties = new HashMap<>();

    private JHipsterModuleBuilder(JHipsterModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.projectFolder = properties.projectFolder();
      this.properties = properties;
      context = JHipsterModuleContext.builder(this);
    }

    JHipsterModuleProperties properties() {
      return properties;
    }

    public JHipsterModuleBuilder documentation(DocumentationTitle title, JHipsterSource source) {
      Assert.notNull("title", title);
      Assert.notNull("source", source);

      String target = "documentation/" + title.filename() + source.extension();
      files().add(source, to(target));

      String markdownLink = "- [" + title.get() + "](" + target + ")";
      optionalReplacements().in(README).add(justLineBefore(text(JHIPSTER_DOCUMENTATION_NEEDLE)), markdownLink);

      return this;
    }

    public JHipsterModuleBuilder readmeSection(String section) {
      Assert.notBlank("section", section);

      optionalReplacements().in(README).add(justLineBefore(text(JHIPSTER_README_SECTION_NEEDLE)), section);

      return this;
    }

    public JHipsterModuleContextBuilder context() {
      return context;
    }

    public JHipsterModuleFilesBuilder files() {
      return files;
    }

    public JHipsterModuleMandatoryReplacementsBuilder mandatoryReplacements() {
      return mandatoryReplacements;
    }

    public JHipsterModuleOptionalReplacementsBuilder optionalReplacements() {
      return optionalReplacements;
    }

    public JHipsterModuleJavaDependenciesBuilder javaDependencies() {
      return javaDependencies;
    }

    public JHipsterModuleJavaBuildPluginBuilder javaBuildPlugins() {
      return javaBuildPlugins;
    }

    public JHipsterModulePackageJsonBuilder packageJson() {
      return packageJson;
    }

    public JHipsterModulePreActionsBuilder preActions() {
      return preActions;
    }

    public JHipsterModulePostActionsBuilder postActions() {
      return postActions;
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties() {
      return springMainProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties() {
      return springMainBootstrapProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainBootstrapProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.MAIN_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestBootstrapProperties() {
      return springProperties.computeIfAbsent(
        new PropertiesKey(SpringProfile.DEFAULT, SpringPropertyType.TEST_BOOTSTRAP_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull(PROFILE, profile);

      return springProperties.computeIfAbsent(
        new PropertiesKey(profile, SpringPropertyType.TEST_PROPERTIES),
        key -> JHipsterModuleSpringProperties.builder(this)
      );
    }

    public JHipsterModule build() {
      return new JHipsterModule(this);
    }
  }

  private static record PropertiesKey(SpringProfile profile, SpringPropertyType type) {}
}
