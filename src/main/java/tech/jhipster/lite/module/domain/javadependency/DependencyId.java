package tech.jhipster.lite.module.domain.javadependency;

import java.util.Optional;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;

public record DependencyId(GroupId groupId, ArtifactId artifactId, Optional<JavaDependencyClassifier> classifier) {
  public DependencyId {
    Assert.notNull("groupId", groupId);
    Assert.notNull("artifactId", artifactId);
    Assert.notNull("classifier", classifier);
  }
}
