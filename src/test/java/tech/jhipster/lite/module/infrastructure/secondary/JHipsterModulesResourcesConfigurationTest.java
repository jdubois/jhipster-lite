package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;

@UnitTest
class JHipsterModulesResourcesConfigurationTest {

  private static final JHipsterModulesResourcesConfiguration configuration = new JHipsterModulesResourcesConfiguration();

  @Test
  void shouldGetAllResourcesWithoutHiddenResources() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setSlugs(null);
    hiddenResources.setTags(null);

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactlyElementsOf(moduleResourcesCollection());
  }

  @Test
  void shouldHideResourcesBySlugs() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setSlugs(List.of("another-module", "yet-another-module"));

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }

  @Test
  void shouldHideResourcesByTags() {
    JHipsterHiddenResourcesProperties hiddenResources = new JHipsterHiddenResourcesProperties();
    hiddenResources.setTags(List.of("tag2", "tag3"));

    JHipsterModulesResources resources = configuration.jhipsterModulesResources(hiddenResources, moduleResourcesCollection());

    assertThat(resources.stream()).usingRecursiveFieldByFieldElementComparator().containsExactly(defaultModuleResource());
  }
}
