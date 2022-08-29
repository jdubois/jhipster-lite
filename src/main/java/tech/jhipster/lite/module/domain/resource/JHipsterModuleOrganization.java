package tech.jhipster.lite.module.domain.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.landscape.JHipsterFeatureDependency;
import tech.jhipster.lite.module.domain.landscape.JHipsterLandscapeDependency;
import tech.jhipster.lite.module.domain.landscape.JHipsterModuleDependency;

public class JHipsterModuleOrganization {

  public static final JHipsterModuleOrganization STANDALONE = builder().build();
  public static final JHipsterModuleOrganization SPRINGBOOT_DEPENDENCY = builder().addModuleDependency("spring-boot").build();

  private final Optional<JHipsterFeatureSlug> feature;
  private final Collection<JHipsterLandscapeDependency> dependencies;

  private JHipsterModuleOrganization(JHipsterModuleOrganizationBuilder builder) {
    feature = builder.feature;
    dependencies = builder.dependencies;
  }

  public static JHipsterModuleOrganizationBuilder builder() {
    return new JHipsterModuleOrganizationBuilder();
  }

  public Optional<JHipsterFeatureSlug> feature() {
    return feature;
  }

  public Collection<JHipsterLandscapeDependency> dependencies() {
    return dependencies;
  }

  public static class JHipsterModuleOrganizationBuilder {

    private final Collection<JHipsterLandscapeDependency> dependencies = new ArrayList<>();
    private Optional<JHipsterFeatureSlug> feature = Optional.empty();

    public JHipsterModuleOrganizationBuilder feature(String feature) {
      this.feature = JHipsterFeatureSlug.of(feature);

      return this;
    }

    public JHipsterModuleOrganizationBuilder addModuleDependency(String module) {
      dependencies.add(new JHipsterModuleDependency(new JHipsterModuleSlug(module)));

      return this;
    }

    public JHipsterModuleOrganizationBuilder addFeatureDependency(String feature) {
      dependencies.add(new JHipsterFeatureDependency(new JHipsterFeatureSlug(feature)));

      return this;
    }

    public JHipsterModuleOrganization build() {
      return new JHipsterModuleOrganization(this);
    }
  }
}
