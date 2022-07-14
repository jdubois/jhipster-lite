package tech.jhipster.lite.module.domain;

public interface JHipsterProjectFolderFactory {
  boolean isInvalid(String folderPath);

  String generatePath();
}
