package tech.jhipster.lite.generator.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Function;

@Schema(name = "JHipsterModuleCategory", description = "Information for a module category")
class RestJHipsterModuleCategory {

  private static final Comparator<RestJHipsterModule> MODULE_COMPARATOR = Comparator.comparing(RestJHipsterModule::getSlug);

  private final String name;
  private final Collection<RestJHipsterModule> modules;

  private RestJHipsterModuleCategory(String name, Collection<RestJHipsterModule> modules) {
    this.name = name;
    this.modules = modules;
  }

  static RestJHipsterModuleCategory from(String category, Collection<JHipsterModuleResource> modules) {
    return new RestJHipsterModuleCategory(category, modules.stream().map(toCategory()).sorted(MODULE_COMPARATOR).toList());
  }

  private static Function<JHipsterModuleResource, RestJHipsterModule> toCategory() {
    return module -> RestJHipsterModule.from(module.slug(), module.apiDoc().operation());
  }

  @Schema(description = "Name of this category", required = true)
  public String getName() {
    return name;
  }

  @Schema(description = "Modules in this category", required = true)
  public Collection<RestJHipsterModule> getModules() {
    return modules;
  }
}
