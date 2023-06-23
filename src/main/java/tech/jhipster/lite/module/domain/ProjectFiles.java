package tech.jhipster.lite.module.domain;

public interface ProjectFiles {
  String readString(String path);

  byte[] readBytes(String path);
}
