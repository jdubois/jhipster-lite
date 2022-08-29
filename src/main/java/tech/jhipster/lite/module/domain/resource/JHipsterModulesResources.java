package tech.jhipster.lite.module.domain.resource;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class JHipsterModulesResources {

  private final Map<JHipsterModuleSlug, JHipsterModuleResource> resources;

  public JHipsterModulesResources(Collection<JHipsterModuleResource> modulesResources) {
    Assert.field("modulesResources", modulesResources).notEmpty().noNullElement();

    assertUniqueSlugs(modulesResources);

    resources =
      Collections.unmodifiableMap(
        modulesResources
          .stream()
          .collect(Collectors.toMap(JHipsterModuleResource::slug, Function.identity(), (x, y) -> y, LinkedHashMap::new))
      );
  }

  private void assertUniqueSlugs(Collection<JHipsterModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<JHipsterModuleResource> modulesResources) {
    return modulesResources.stream().map(JHipsterModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  public Stream<JHipsterModuleResource> stream() {
    return resources.values().stream();
  }

  public JHipsterModule build(JHipsterModuleSlug slug, JHipsterModuleProperties properties) {
    Assert.notNull("slug", slug);
    Assert.notNull("properties", properties);

    return resources.get(slug).factory().create(properties);
  }
}
