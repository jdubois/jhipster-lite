package tech.jhipster.lite.project.domain.download;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.ProjectsRepository;
import tech.jhipster.lite.project.domain.UnknownProjectException;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

public class ProjectsDownloader {

  private final ProjectFolder projectFolder;
  private final ProjectsRepository projects;

  public ProjectsDownloader(ProjectFolder projectFolder, ProjectsRepository projects) {
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("projects", projects);

    this.projectFolder = projectFolder;
    this.projects = projects;
  }

  public Project download(ProjectPath path) {
    assertValidPath(path);

    return projects.get(path).orElseThrow(UnknownProjectException::new);
  }

  private void assertValidPath(ProjectPath path) {
    if (projectFolder.isInvalid(path.get())) {
      throw new InvalidDownloadException();
    }
  }
}
