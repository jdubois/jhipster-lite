package tech.jhipster.lite.projectfile.domain;

public interface ProjectFilesReader {
  String readString(String path);

  byte[] readBytes(String path);
}
