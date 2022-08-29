package tech.jhipster.lite.module.domain.resource;

import static tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.module.domain.JHipsterModuleFactory;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleOrganization.JHipsterModuleOrganizationBuilder;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleTags.JHipsterModuleTagsBuilder;

public final class JHipsterModulesResourceFixture {

  private JHipsterModulesResourceFixture() {}

  public static JHipsterModulesResources moduleResources() {
    return new JHipsterModulesResources(
      List.of(
        defaultModuleResource(),
        defaultModuleResourceBuilder().slug("another-module").tags(new JHipsterModuleTagsBuilder().add("tag2").build()).build(),
        defaultModuleResourceBuilder()
          .slug("yet-another-module")
          .tag("Another tag")
          .operation("Another operation")
          .tags(new JHipsterModuleTagsBuilder().add("tag3").build())
          .build()
      )
    );
  }

  public static JHipsterModuleResource defaultModuleResource() {
    return defaultModuleResourceBuilder().build();
  }

  public static JHipsterTestModuleResourceBuilder defaultModuleResourceBuilder() {
    return new JHipsterTestModuleResourceBuilder()
      .slug("slug")
      .operation("operation")
      .tags(JHipsterModuleTags.builder().add("tag1").build())
      .factory(properties -> null);
  }

  public static JHipsterModulePropertiesDefinition propertiesDefinition() {
    return JHipsterModulePropertiesDefinition
      .builder()
      .addBasePackage()
      .addIndentation()
      .addProjectName()
      .addProjectBaseName()
      .add(optionalStringProperty("optionalString").build())
      .add(mandatoryIntegerProperty("mandatoryInteger").build())
      .add(mandatoryBooleanProperty("mandatoryBoolean").build())
      .add(optionalBooleanProperty("optionalBoolean").build())
      .build();
  }

  public static class JHipsterTestModuleResourceBuilder {

    private String slug;
    private String tag = "tag";
    private String operation;
    private JHipsterModuleFactory factory;
    private JHipsterModuleTags tags;
    private String feature;

    private final Collection<String> moduleDependencies = new ArrayList<>();
    private final Collection<String> featureDependencies = new ArrayList<>();

    private JHipsterTestModuleResourceBuilder() {}

    public JHipsterTestModuleResourceBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public JHipsterTestModuleResourceBuilder tag(String tag) {
      this.tag = tag;

      return this;
    }

    public JHipsterTestModuleResourceBuilder operation(String operation) {
      this.operation = operation;

      return this;
    }

    public JHipsterTestModuleResourceBuilder factory(JHipsterModuleFactory factory) {
      this.factory = factory;

      return this;
    }

    public JHipsterTestModuleResourceBuilder feature(String feature) {
      this.feature = feature;

      return this;
    }

    public JHipsterTestModuleResourceBuilder moduleDependency(String module) {
      moduleDependencies.add(module);

      return this;
    }

    public JHipsterTestModuleResourceBuilder featureDependency(String feature) {
      featureDependencies.add(feature);

      return this;
    }

    public JHipsterTestModuleResourceBuilder tags(JHipsterModuleTags tags) {
      this.tags = tags;

      return this;
    }

    public JHipsterModuleResource build() {
      return JHipsterModuleResource
        .builder()
        .slug(slug)
        .propertiesDefinition(JHipsterModulesResourceFixture.propertiesDefinition())
        .apiDoc(new JHipsterModuleApiDoc(tag, operation))
        .organization(buildOrganization())
        .tags(tags)
        .factory(factory);
    }

    private JHipsterModuleOrganization buildOrganization() {
      JHipsterModuleOrganizationBuilder builder = JHipsterModuleOrganization.builder().feature(feature);

      moduleDependencies.forEach(builder::addModuleDependency);
      featureDependencies.forEach(builder::addFeatureDependency);

      return builder.build();
    }
  }
}
