package tech.jhipster.lite.generator.setup.codespaces.domain;

import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class CodespacesDomainService implements CodespacesService {

  public static final String SOURCE = "setup/codespaces";
  public static final String DEVCONTAINER_DEST = ".devcontainer";

  private final ProjectRepository projectRepository;

  public CodespacesDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void init(Project project) {
    addConfig(project);
    addDockerfile(project);
  }

  private void addConfig(Project project) {
    project.addConfig("serverPort", 8080);
    projectRepository.template(
      ProjectFile.forProject(project).withSource(SOURCE, "devcontainer.json").withDestination(DEVCONTAINER_DEST, "devcontainer.json")
    );
  }

  private void addDockerfile(Project project) {
    projectRepository.add(ProjectFile.forProject(project).withSource(SOURCE, "Dockerfile").withDestinationFolder(DEVCONTAINER_DEST));
  }
}
