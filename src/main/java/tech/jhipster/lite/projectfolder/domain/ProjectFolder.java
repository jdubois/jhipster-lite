package tech.jhipster.lite.projectfolder.domain;

public interface ProjectFolder {
  boolean isInvalid(String folderPath);

  String generatePath();
}
