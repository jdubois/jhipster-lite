package tech.jhipster.lite.module.infrastructure.primary;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import tech.jhipster.lite.error.domain.Assert;

record JHipsterModulesResources(Collection<JHipsterModuleResource> modulesResources) {
  public JHipsterModulesResources {
    Assert.field("modulesrouces", modulesResources).noNullElement().notEmpty();

    assertUniqSlugs(modulesResources);
  }

  private void assertUniqSlugs(Collection<JHipsterModuleResource> modulesResources) {
    if (duplicatedSlug(modulesResources)) {
      throw new DuplicatedSlugException();
    }
  }

  private boolean duplicatedSlug(Collection<JHipsterModuleResource> modulesResources) {
    return modulesResources.stream().map(JHipsterModuleResource::slug).collect(Collectors.toSet()).size() != modulesResources.size();
  }

  public Stream<JHipsterModuleResource> stream() {
    return modulesResources().stream();
  }
}
