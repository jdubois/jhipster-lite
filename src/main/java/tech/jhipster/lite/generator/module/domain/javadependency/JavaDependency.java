package tech.jhipster.lite.generator.module.domain.javadependency;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependencyCommand;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;

public class JavaDependency {

  private final DependencyId id;
  private final Optional<VersionSlug> versionSlug;
  private final JavaDependencyScope scope;
  private final boolean optional;

  private JavaDependency(JavaDependencyBuilder builder) {
    id = new DependencyId(builder.groupId, builder.artifactId);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    scope = JavaDependencyScope.from(builder.scope);
    optional = builder.optional;
  }

  public static JavaDependencyGroupIdBuilder builder() {
    return new JavaDependencyBuilder();
  }

  JavaDependenciesCommands changeCommands(CurrentJavaDependenciesVersions currentVersions, ProjectJavaDependencies projectDependencies) {
    Assert.notNull("currentVersion", currentVersions);
    Assert.notNull("projectDependencies", projectDependencies);

    Collection<JavaDependencyCommand> versionCommands = versionCommands(currentVersions, projectDependencies);
    Collection<JavaDependencyCommand> dependencyCommands = dependencyCommands(projectDependencies);

    return new JavaDependenciesCommands(Stream.of(versionCommands, dependencyCommands).flatMap(Collection::stream).toList());
  }

  private Collection<JavaDependencyCommand> versionCommands(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return versionSlug.flatMap(toVersion(currentVersions, projectDependencies)).map(toSetVersionCommand()).map(List::of).orElse(List.of());
  }

  private Function<VersionSlug, Optional<JavaDependencyVersion>> toVersion(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return slug -> {
      JavaDependencyVersion currentVersion = currentVersions.get(slug);

      return projectDependencies.version(slug).map(toVersionToUse(currentVersion)).orElseGet(() -> Optional.of(currentVersion));
    };
  }

  private Function<JavaDependencyVersion, Optional<JavaDependencyVersion>> toVersionToUse(JavaDependencyVersion currentVersion) {
    return version -> {
      if (version.equals(currentVersion)) {
        return Optional.empty();
      }

      return Optional.of(currentVersion);
    };
  }

  private Function<JavaDependencyVersion, JavaDependencyCommand> toSetVersionCommand() {
    return SetJavaDependencyVersion::new;
  }

  private Collection<JavaDependencyCommand> dependencyCommands(ProjectJavaDependencies projectDependencies) {
    return projectDependencies.dependency(id).map(toDepencendiesCommands()).orElseGet(() -> List.of(new AddJavaDependency(this)));
  }

  private Function<JavaDependency, Collection<JavaDependencyCommand>> toDepencendiesCommands() {
    return projectDependency -> {
      JavaDependency resultingDependency = merge(projectDependency);

      if (resultingDependency.equals(projectDependency)) {
        return List.of();
      }

      return List.of(new RemoveJavaDependency(id), new AddJavaDependency(resultingDependency));
    };
  }

  private JavaDependency merge(JavaDependency other) {
    return JavaDependency
      .builder()
      .groupId(groupId())
      .artifactId(artifactId())
      .versionSlug(mergeVersionsSlugs(other))
      .scope(mergeScopes(other))
      .optional()
      .optional(mergeOptionalFlag(other))
      .build();
  }

  private VersionSlug mergeVersionsSlugs(JavaDependency other) {
    return versionSlug.orElseGet(() -> other.versionSlug.orElse(null));
  }

  private JavaDependencyScope mergeScopes(JavaDependency other) {
    return scope.merge(other.scope);
  }

  private boolean mergeOptionalFlag(JavaDependency other) {
    return optional && other.optional;
  }

  public DependencyId id() {
    return id;
  }

  public GroupId groupId() {
    return id.groupId();
  }

  public ArtifactId artifactId() {
    return id.artifactId();
  }

  public Optional<VersionSlug> version() {
    return versionSlug;
  }

  public boolean optional() {
    return optional;
  }

  public JavaDependencyScope scope() {
    return scope;
  }

  @Override
  @Generated
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(versionSlug).append(scope).append(optional).hashCode();
  }

  @Override
  @Generated
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }

    JavaDependency other = (JavaDependency) obj;

    return new EqualsBuilder()
      .append(id, other.id)
      .append(versionSlug, other.versionSlug)
      .append(scope, other.scope)
      .append(optional, other.optional)
      .isEquals();
  }

  public static class JavaDependencyBuilder
    implements JavaDependencyGroupIdBuilder, JavaDependencyArtifactIdBuilder, JavaDependencyOptionalValueBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private VersionSlug versionSlug;
    private JavaDependencyScope scope;
    private boolean optional;

    private JavaDependencyBuilder() {}

    @Override
    public JavaDependencyArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder scope(JavaDependencyScope scope) {
      this.scope = scope;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder optional(boolean optional) {
      this.optional = optional;

      return this;
    }

    @Override
    public JavaDependency build() {
      return new JavaDependency(this);
    }
  }

  public interface JavaDependencyGroupIdBuilder {
    JavaDependencyArtifactIdBuilder groupId(GroupId groupId);

    default JavaDependencyArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface JavaDependencyArtifactIdBuilder {
    JavaDependencyOptionalValueBuilder artifactId(ArtifactId artifactId);

    default JavaDependencyOptionalValueBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface JavaDependencyOptionalValueBuilder {
    JavaDependencyOptionalValueBuilder versionSlug(VersionSlug versionSlug);

    JavaDependencyOptionalValueBuilder scope(JavaDependencyScope scope);

    JavaDependencyOptionalValueBuilder optional(boolean optional);

    JavaDependency build();

    default JavaDependencyOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }

    default JavaDependencyOptionalValueBuilder optional() {
      return optional(true);
    }
  }
}
