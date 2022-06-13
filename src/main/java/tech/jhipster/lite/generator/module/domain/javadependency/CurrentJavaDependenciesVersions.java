package tech.jhipster.lite.generator.module.domain.javadependency;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import tech.jhipster.lite.error.domain.Assert;

public class CurrentJavaDependenciesVersions {

  private final Map<String, JavaDependencyVersion> versions;

  public CurrentJavaDependenciesVersions(Collection<JavaDependencyVersion> versions) {
    Assert.field("versions", versions).notEmpty().noNullElement();

    this.versions = versions.stream().collect(Collectors.toUnmodifiableMap(version -> version.slug().propertyName(), Function.identity()));
  }

  public JavaDependencyVersion get(VersionSlug slug) {
    Assert.notNull("slug", slug);

    return Optional.ofNullable(versions.get(slug.propertyName())).orElseThrow(() -> new UnknownJavaVersionSlugException(slug));
  }
}
