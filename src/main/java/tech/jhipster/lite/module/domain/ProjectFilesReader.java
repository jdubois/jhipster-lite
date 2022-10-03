package tech.jhipster.lite.module.domain;

public interface ProjectFilesReader {
  String readString(String path);

  byte[] readBytes(String path);
}
