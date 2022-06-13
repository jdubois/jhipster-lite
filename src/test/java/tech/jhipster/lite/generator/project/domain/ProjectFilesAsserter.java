package tech.jhipster.lite.generator.project.domain;

import static org.mockito.ArgumentMatchers.*;

import java.util.Collection;

public final class ProjectFilesAsserter {

  private ProjectFilesAsserter() {}

  public static Collection<ProjectFile> filesCountArgument(int count) {
    return argThat(files -> {
      return files.size() == count;
    });
  }

  public static ProjectFile projectFileArgument(Project project, FilePath source, FilePath destination) {
    return argThat(file -> {
      boolean sameProject = file.project().equals(project);
      boolean sameSource = file.source().equals(source);
      boolean sameDestination = file.destination().equals(destination);

      return sameProject && sameSource && sameDestination;
    });
  }
}
