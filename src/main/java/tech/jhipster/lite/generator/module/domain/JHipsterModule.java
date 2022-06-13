package tech.jhipster.lite.generator.module.domain;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.JHipsterModuleContext.JHipsterModuleContextBuilder;
import tech.jhipster.lite.generator.module.domain.JHipsterModuleFiles.JHipsterModuleFilesBuilder;
import tech.jhipster.lite.generator.module.domain.JHipsterModulePreActions.JHipsterModulePreActionsBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.ArtifactId;
import tech.jhipster.lite.generator.module.domain.javadependency.GroupId;
import tech.jhipster.lite.generator.module.domain.javadependency.JHipsterModuleJavaDependencies;
import tech.jhipster.lite.generator.module.domain.javadependency.JHipsterModuleJavaDependencies.JHipsterModuleJavaDependenciesBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependency.JavaDependencyGroupIdBuilder;
import tech.jhipster.lite.generator.module.domain.javadependency.VersionSlug;
import tech.jhipster.lite.generator.module.domain.javaproperties.JHipsterModuleSpringProperties;
import tech.jhipster.lite.generator.module.domain.javaproperties.JHipsterModuleSpringProperties.JHipsterModuleSpringPropertiesBuilder;
import tech.jhipster.lite.generator.module.domain.javaproperties.PropertyKey;
import tech.jhipster.lite.generator.module.domain.javaproperties.PropertyValue;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProfile;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperties;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperty;
import tech.jhipster.lite.generator.module.domain.javaproperties.SpringProperty.SpringPropertyKeyBuilder;
import tech.jhipster.lite.generator.module.domain.postaction.JHipsterModulePostActions;
import tech.jhipster.lite.generator.module.domain.postaction.JHipsterModulePostActions.JHipsterModulePostActionsBuilder;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;
import tech.jhipster.lite.generator.module.domain.replacement.JHipsterModuleMandatoryReplacements;
import tech.jhipster.lite.generator.module.domain.replacement.JHipsterModuleMandatoryReplacements.JHipsterModuleMandatoryReplacementsBuilder;
import tech.jhipster.lite.generator.module.domain.replacement.JHipsterModuleOptionalReplacements;
import tech.jhipster.lite.generator.module.domain.replacement.JHipsterModuleOptionalReplacements.JHipsterModuleOptionalReplacementsBuilder;
import tech.jhipster.lite.generator.module.domain.replacement.RegexMatcher;
import tech.jhipster.lite.generator.module.domain.replacement.TextMatcher;

public class JHipsterModule {

  private final JHipsterProjectFolder projectFolder;
  private final Collection<JHipsterModuleFile> files;
  private final JHipsterModuleMandatoryReplacements mandatoryReplacements;
  private final JHipsterModuleOptionalReplacements optionalReplacements;
  private final JHipsterModuleContext context;
  private final JHipsterModuleJavaDependencies javaDependencies;
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
    preActions = builder.preActions.build();
    postActions = builder.postActions.build();
    springProperties = buildSpringProperties(builder);
  }

  private SpringProperties buildSpringProperties(JHipsterModuleBuilder builder) {
    Stream<SpringProperty> mainProperties = builder.mainSpringProperties
      .entrySet()
      .stream()
      .flatMap(toProperties(SpringProperty::mainPropertyBuilder));

    Stream<SpringProperty> testProperties = builder.testSpringProperties
      .entrySet()
      .stream()
      .flatMap(toProperties(SpringProperty::testPropertyBuilder));

    return new SpringProperties(Stream.concat(mainProperties, testProperties).toList());
  }

  private Function<Entry<SpringProfile, JHipsterModuleSpringPropertiesBuilder>, Stream<SpringProperty>> toProperties(
    Supplier<SpringPropertyKeyBuilder> builderFactory
  ) {
    return properties -> properties.getValue().build().properties().entrySet().stream().map(toProperty(builderFactory, properties));
  }

  private Function<Entry<PropertyKey, PropertyValue>, SpringProperty> toProperty(
    Supplier<SpringPropertyKeyBuilder> builderFactory,
    Entry<SpringProfile, JHipsterModuleSpringPropertiesBuilder> properties
  ) {
    return property -> builderFactory.get().key(property.getKey()).value(property.getValue()).profile(properties.getKey()).build();
  }

  public static JHipsterModuleBuilder moduleForProject(JHipsterModuleProperties properties) {
    return new JHipsterModuleBuilder(properties);
  }

  public static JavaDependencyGroupIdBuilder javaDependency() {
    return JavaDependency.builder();
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

    private static final String JHIPSTER_DOCUMENTATION_NEEDLE = "<!-- jhipster-needle-documentation -->";

    private final JHipsterProjectFolder projectFolder;
    private final JHipsterModuleProperties properties;
    private final JHipsterModuleContextBuilder context;
    private final JHipsterModuleFilesBuilder files = JHipsterModuleFiles.builder(this);
    private final JHipsterModuleMandatoryReplacementsBuilder mandatoryReplacements = JHipsterModuleMandatoryReplacements.builder(this);
    private final JHipsterModuleOptionalReplacementsBuilder optionalReplacements = JHipsterModuleOptionalReplacements.builder(this);
    private final JHipsterModuleJavaDependenciesBuilder javaDependencies = JHipsterModuleJavaDependencies.builder(this);
    private final JHipsterModulePreActionsBuilder preActions = JHipsterModulePreActions.builder(this);
    private final JHipsterModulePostActionsBuilder postActions = JHipsterModulePostActions.builder(this);
    private final Map<SpringProfile, JHipsterModuleSpringPropertiesBuilder> mainSpringProperties = new HashMap<>();
    private final Map<SpringProfile, JHipsterModuleSpringPropertiesBuilder> testSpringProperties = new HashMap<>();

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

      String markdownLink = "- [" + title.get() + "](" + target + ") \n\n" + JHIPSTER_DOCUMENTATION_NEEDLE;
      optionalReplacements().in("README.md").add(text(JHIPSTER_DOCUMENTATION_NEEDLE), markdownLink);

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

    public JHipsterModulePreActionsBuilder preActions() {
      return preActions;
    }

    public JHipsterModulePostActionsBuilder postActions() {
      return postActions;
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties() {
      return springMainProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springMainProperties(SpringProfile profile) {
      Assert.notNull("profile", profile);

      return mainSpringProperties.computeIfAbsent(profile, key -> JHipsterModuleSpringProperties.builder(this));
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties() {
      return springTestProperties(SpringProfile.DEFAULT);
    }

    public JHipsterModuleSpringPropertiesBuilder springTestProperties(SpringProfile profile) {
      Assert.notNull("profile", profile);

      return testSpringProperties.computeIfAbsent(profile, key -> JHipsterModuleSpringProperties.builder(this));
    }

    public JHipsterModule build() {
      return new JHipsterModule(this);
    }
  }
}
