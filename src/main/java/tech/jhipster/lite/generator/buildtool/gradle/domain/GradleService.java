package tech.jhipster.lite.generator.buildtool.gradle.domain;

import java.util.Optional;

public interface GradleService {
  Optional<String> getGroup(String folder);
}
