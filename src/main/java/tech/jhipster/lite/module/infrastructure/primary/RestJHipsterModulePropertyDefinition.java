package tech.jhipster.lite.module.infrastructure.primary;

import io.swagger.v3.oas.annotations.media.Schema;
import tech.jhipster.lite.module.domain.properties.JHipsterPropertyDefaultValue;
import tech.jhipster.lite.module.domain.properties.JHipsterPropertyDescription;
import tech.jhipster.lite.module.domain.properties.JHipsterPropertyType;
import tech.jhipster.lite.module.domain.resource.JHipsterModulePropertyDefinition;

@Schema(name = "JHipsterModulePropertyDefinition", description = "Definition for a given property")
class RestJHipsterModulePropertyDefinition {

  private final JHipsterPropertyType type;
  private final boolean mandatory;
  private final String key;
  private final String description;
  private final String defaultValue;
  private final int order;

  private RestJHipsterModulePropertyDefinition(RestJHipsterModulePropertyDefinitionBuilder builder) {
    type = builder.type;
    mandatory = builder.mandatory;
    key = builder.key;
    description = builder.description;
    defaultValue = builder.defaultValue;
    order = builder.order;
  }

  static RestJHipsterModulePropertyDefinition from(JHipsterModulePropertyDefinition propertyDefinition) {
    return new RestJHipsterModulePropertyDefinitionBuilder()
      .type(propertyDefinition.type())
      .mandatory(propertyDefinition.isMandatory())
      .key(propertyDefinition.key().get())
      .description(propertyDefinition.description().map(JHipsterPropertyDescription::get).orElse(null))
      .defaultValue(propertyDefinition.defaultValue().map(JHipsterPropertyDefaultValue::get).orElse(null))
      .order(propertyDefinition.order())
      .build();
  }

  @Schema(description = "Type of this property", required = true)
  public JHipsterPropertyType getType() {
    return type;
  }

  @Schema(description = "True if this property is mandatory, false otherwise", required = true)
  public boolean isMandatory() {
    return mandatory;
  }

  @Schema(description = "Key of this property", required = true)
  public String getKey() {
    return key;
  }

  @Schema(description = "Full text description of this property")
  public String getDescription() {
    return description;
  }

  @Schema(description = "Default value for this property")
  public String getDefaultValue() {
    return defaultValue;
  }

  @Schema(description = "Order (sort in natural int sorting) for this property", required = true)
  public int getOrder() {
    return order;
  }

  private static class RestJHipsterModulePropertyDefinitionBuilder {

    private JHipsterPropertyType type;
    private boolean mandatory;
    private String key;
    private String description;
    private String defaultValue;
    private int order;

    public RestJHipsterModulePropertyDefinitionBuilder type(JHipsterPropertyType type) {
      this.type = type;

      return this;
    }

    public RestJHipsterModulePropertyDefinitionBuilder mandatory(boolean mandatory) {
      this.mandatory = mandatory;

      return this;
    }

    public RestJHipsterModulePropertyDefinitionBuilder key(String key) {
      this.key = key;

      return this;
    }

    public RestJHipsterModulePropertyDefinitionBuilder description(String description) {
      this.description = description;

      return this;
    }

    public RestJHipsterModulePropertyDefinitionBuilder defaultValue(String defaultValue) {
      this.defaultValue = defaultValue;

      return this;
    }

    public RestJHipsterModulePropertyDefinitionBuilder order(int order) {
      this.order = order;

      return this;
    }

    public RestJHipsterModulePropertyDefinition build() {
      return new RestJHipsterModulePropertyDefinition(this);
    }
  }
}
