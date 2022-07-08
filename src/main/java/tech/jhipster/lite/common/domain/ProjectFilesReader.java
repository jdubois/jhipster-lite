package tech.jhipster.lite.common.domain;

public interface ProjectFilesReader {
  String readString(String path);

  byte[] readBytes(String path);
}
