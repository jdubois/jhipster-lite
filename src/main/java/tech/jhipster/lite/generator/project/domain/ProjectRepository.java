package tech.jhipster.lite.generator.project.domain;

import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.error.domain.Assert;

public interface ProjectRepository {
  void add(Collection<ProjectFile> files);

  void template(Collection<ProjectFile> files);

  void rename(Project project, String source, String sourceFilename, String destinationFilename);

  boolean containsRegexp(Project project, String source, String sourceFilename, String regexp);

  void replaceText(Project project, String source, String sourceFilename, String oldText, String newText);

  void write(Project project, String text, String destination, String destinationFilename);

  void gitInit(Project project);
  void gitApplyPatch(Project project, String patchFilename);

  String zip(Project project);

  byte[] download(Project project);

  default void add(ProjectFile file) {
    Assert.notNull("file", file);

    add(List.of(file));
  }

  default void template(ProjectFile file) {
    Assert.notNull("file", file);

    template(List.of(file));
  }
}
