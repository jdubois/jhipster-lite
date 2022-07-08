package tech.jhipster.lite.npm.infrastructure.secondary;

enum NpmVersionSourceFolder {
  COMMON("common"),
  ANGULAR("angular"),
  REACT("react"),
  SVELTE("svelte"),
  VUE("vue");

  private final String folder;

  NpmVersionSourceFolder(String folder) {
    this.folder = folder;
  }

  String folder() {
    return folder;
  }
}
