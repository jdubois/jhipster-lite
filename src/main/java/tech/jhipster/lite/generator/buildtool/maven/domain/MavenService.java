package tech.jhipster.lite.generator.buildtool.maven.domain;

import java.util.Optional;

public interface MavenService {
  Optional<String> getVersion(String name);

  Optional<String> getGroupId(String folder);

  Optional<String> getName(String folder);
}
