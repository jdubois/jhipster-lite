package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JHipsterModule", description = "Information for a JHipster module")
class RestJHipsterModule {

  private final String slug;
  private final String description;
  private final RestJHipsterModulePropertiesDefinition properties;

  private RestJHipsterModule(RestJHipsterModuleBuilder builder) {
    slug = builder.slug;
    description = builder.description;
    properties = builder.properties;
  }

  static RestJHipsterModule from(JHipsterModuleResource moduleResource) {
    return new RestJHipsterModuleBuilder()
      .slug(moduleResource.slug().get())
      .description(moduleResource.apiDoc().operation())
      .properties(RestJHipsterModulePropertiesDefinition.from(moduleResource.propertiesDefinition()))
      .build();
  }

  @Schema(description = "Module slug", required = true)
  public String getSlug() {
    return slug;
  }

  @Schema(description = "Module description", required = true)
  public String getDescription() {
    return description;
  }

  @Schema(description = "Properties for this module", required = true)
  public RestJHipsterModulePropertiesDefinition getProperties() {
    return properties;
  }

  private static class RestJHipsterModuleBuilder {

    private String slug;
    private String description;
    private RestJHipsterModulePropertiesDefinition properties;

    public RestJHipsterModuleBuilder slug(String slug) {
      this.slug = slug;

      return this;
    }

    public RestJHipsterModuleBuilder description(String description) {
      this.description = description;

      return this;
    }

    public RestJHipsterModuleBuilder properties(RestJHipsterModulePropertiesDefinition properties) {
      this.properties = properties;

      return this;
    }

    public RestJHipsterModule build() {
      return new RestJHipsterModule(this);
    }
  }
}
