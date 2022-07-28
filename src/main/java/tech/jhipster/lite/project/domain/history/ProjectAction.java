package tech.jhipster.lite.project.domain.history;

import java.time.Instant;
import java.util.Map;
import tech.jhipster.lite.error.domain.Assert;

public class ProjectAction {

  private final ModuleSlug module;
  private final Instant date;
  private final ModuleProperties properties;

  private ProjectAction(ProjectActionBuilder builder) {
    Assert.notNull("module", builder.module);
    Assert.notNull("date", builder.date);
    Assert.notNull("properties", builder.properties);

    module = builder.module;
    date = builder.date;
    properties = builder.properties;
  }

  public static ProjectActionModuleBuilder builder() {
    return new ProjectActionBuilder();
  }

  public ModuleSlug module() {
    return module;
  }

  public Instant date() {
    return date;
  }

  public ModuleProperties properties() {
    return properties;
  }

  public static class ProjectActionBuilder implements ProjectActionModuleBuilder, ProjectActionDateBuilder, ProjectActionPropertiesBuilder {

    private ModuleSlug module;
    private Instant date;
    private ModuleProperties properties;

    @Override
    public ProjectActionDateBuilder module(ModuleSlug module) {
      this.module = module;

      return this;
    }

    @Override
    public ProjectActionPropertiesBuilder date(Instant date) {
      this.date = date;

      return this;
    }

    @Override
    public ProjectAction properties(ModuleProperties properties) {
      this.properties = properties;

      return new ProjectAction(this);
    }
  }

  public interface ProjectActionModuleBuilder {
    ProjectActionDateBuilder module(ModuleSlug module);

    default ProjectActionDateBuilder module(String module) {
      return module(new ModuleSlug(module));
    }
  }

  public interface ProjectActionDateBuilder {
    ProjectActionPropertiesBuilder date(Instant date);
  }

  public interface ProjectActionPropertiesBuilder {
    ProjectAction properties(ModuleProperties properties);

    default ProjectAction properties(Map<String, Object> properties) {
      return properties(new ModuleProperties(properties));
    }
  }
}
