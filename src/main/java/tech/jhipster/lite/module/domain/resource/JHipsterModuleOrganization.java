package tech.jhipster.lite.module.domain.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterFeatureSlug;
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

    /**
     * @deprecated use {@link #feature(JHipsterFeatureSlugFactory)} instead
     */
    @Deprecated(forRemoval = true)
    public JHipsterModuleOrganizationBuilder feature(String feature) {
      return feature(() -> feature);
    }

    public JHipsterModuleOrganizationBuilder feature(JHipsterFeatureSlugFactory feature) {
      Assert.notNull("feature", feature);

      this.feature = feature.build();

      return this;
    }

    /**
     * @deprecated use {@link #addDependency(JHipsterModuleSlugFactory)} instead
     */
    @Deprecated(forRemoval = true)
    public JHipsterModuleOrganizationBuilder addModuleDependency(String module) {
      return addDependency((JHipsterModuleSlugFactory) () -> module);
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterModuleSlugFactory module) {
      Assert.notNull("module", module);

      dependencies.add(new JHipsterModuleDependency(module.build()));

      return this;
    }

    /**
     * @deprecated use {@link #addDependency(JHipsterFeatureSlugFactory)} instead
     */
    @Deprecated(forRemoval = true)
    public JHipsterModuleOrganizationBuilder addFeatureDependency(String feature) {
      return addDependency((JHipsterFeatureSlugFactory) () -> feature);
    }

    public JHipsterModuleOrganizationBuilder addDependency(JHipsterFeatureSlugFactory feature) {
      Assert.notNull("feature", feature);

      feature.build().map(JHipsterFeatureDependency::new).ifPresent(dependencies::add);

      return this;
    }

    public JHipsterModuleOrganization build() {
      return new JHipsterModuleOrganization(this);
    }
  }
}
