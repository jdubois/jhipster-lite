package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Map;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

class RestJHipsterModuleProperties {

  private final String projectFolder;
  private final boolean commit;
  private final Map<String, Object> parameters;

  RestJHipsterModuleProperties(
    @JsonProperty("projectFolder") String projectFolder,
    @JsonProperty("commit") boolean commit,
    @JsonProperty("parameters") Map<String, Object> parameters
  ) {
    this.projectFolder = projectFolder;
    this.commit = commit;
    this.parameters = parameters;
  }

  public JHipsterModuleProperties toDomain(ProjectFolder jHipsterProjectFolderFactory) {
    Assert.notNull("jHipsterProjectFolderFactory", jHipsterProjectFolderFactory);

    assertValidProjectFolder(jHipsterProjectFolderFactory);

    return new JHipsterModuleProperties(getProjectFolder(), isCommit(), getParameters());
  }

  private void assertValidProjectFolder(ProjectFolder jHipsterProjectFolderFactory) {
    if (jHipsterProjectFolderFactory.isInvalid(projectFolder)) {
      throw new InvalidProjectFolderException();
    }
  }

  @Schema(description = "Path to the project folder", required = true)
  public String getProjectFolder() {
    return projectFolder;
  }

  @Schema(description = "True to commit each module application, false otherwise", required = true)
  public boolean isCommit() {
    return commit;
  }

  @Schema(description = "Parameters to apply on modules")
  public Map<String, Object> getParameters() {
    return parameters;
  }
}
