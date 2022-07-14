package tech.jhipster.lite.module.domain.javadependency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javabuild.command.JavaBuildCommand;
import tech.jhipster.lite.module.domain.javabuild.command.SetVersion;

public class JavaDependency {

  private final DependencyId id;
  private final Optional<VersionSlug> versionSlug;
  private final JavaDependencyScope scope;
  private final boolean optional;
  private final Optional<JavaDependencyType> type;
  private final Collection<DependencyId> exclusions;

  private JavaDependency(JavaDependencyBuilder builder) {
    id = new DependencyId(builder.groupId, builder.artifactId);
    versionSlug = Optional.ofNullable(builder.versionSlug);
    scope = JavaDependencyScope.from(builder.scope);
    optional = builder.optional;
    type = Optional.ofNullable(builder.type);
    exclusions = JHipsterCollections.immutable(builder.exclusions);
  }

  public static JavaDependencyGroupIdBuilder builder() {
    return new JavaDependencyBuilder();
  }

  Collection<JavaBuildCommand> versionCommands(
    CurrentJavaDependenciesVersions currentVersions,
    ProjectJavaDependencies projectDependencies
  ) {
    return version().flatMap(toVersion(currentVersions, projectDependencies)).map(toSetVersionCommand()).map(List::of).orElse(List.of());
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

  private Function<JavaDependencyVersion, JavaBuildCommand> toSetVersionCommand() {
    return SetVersion::new;
  }

  Collection<JavaBuildCommand> dependencyCommands(DependenciesCommandsFactory commands, Optional<JavaDependency> projectDependency) {
    return projectDependency.map(toDependenciesCommands(commands)).orElseGet(() -> List.of(commands.addDependency(this)));
  }

  private Function<JavaDependency, Collection<JavaBuildCommand>> toDependenciesCommands(DependenciesCommandsFactory commands) {
    return projectDependency -> {
      Collection<JavaDependency> resultingDependencies = merge(projectDependency);

      if (resultingDependencies.size() == 1 && resultingDependencies.contains(projectDependency)) {
        return List.of();
      }

      return Stream
        .concat(Stream.of(commands.removeDependency(id())), resultingDependencies.stream().map(commands::addDependency))
        .toList();
    };
  }

  public DependencyId id() {
    return id;
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

  public Optional<JavaDependencyType> type() {
    return type;
  }

  public Collection<DependencyId> exclusions() {
    return exclusions;
  }

  private Collection<JavaDependency> merge(JavaDependency other) {
    JavaDependency mergedWithCurrentType = merge(other, type);

    if (!type.equals(other.type)) {
      return List.of(mergedWithCurrentType, merge(other, other.type));
    }

    return List.of(mergedWithCurrentType);
  }

  private JavaDependency merge(JavaDependency other, Optional<JavaDependencyType> resultingType) {
    return JavaDependency
      .builder()
      .groupId(groupId())
      .artifactId(artifactId())
      .versionSlug(mergeVersionsSlugs(other))
      .scope(mergeScopes(other))
      .optional(mergeOptionalFlag(other))
      .type(resultingType.orElse(null))
      .build();
  }

  private GroupId groupId() {
    return id.groupId();
  }

  private ArtifactId artifactId() {
    return id.artifactId();
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

  @Override
  @Generated
  public int hashCode() {
    return new HashCodeBuilder().append(id).append(versionSlug).append(scope).append(optional).append(type).hashCode();
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
      .append(type, other.type)
      .isEquals();
  }

  public static class JavaDependencyBuilder
    implements JavaDependencyGroupIdBuilder, JavaDependencyArtifactIdBuilder, JavaDependencyOptionalValueBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private VersionSlug versionSlug;
    private JavaDependencyScope scope;
    private boolean optional;
    private JavaDependencyType type;
    private final Collection<DependencyId> exclusions = new ArrayList<>();

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
    public JavaDependencyOptionalValueBuilder type(JavaDependencyType type) {
      this.type = type;

      return this;
    }

    @Override
    public JavaDependencyOptionalValueBuilder addExclusion(DependencyId dependency) {
      Assert.notNull("dependency", dependency);

      exclusions.add(dependency);

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

    JavaDependencyOptionalValueBuilder type(JavaDependencyType type);

    JavaDependencyOptionalValueBuilder addExclusion(DependencyId dependency);

    JavaDependency build();

    default JavaDependencyOptionalValueBuilder versionSlug(String versionSlug) {
      return versionSlug(VersionSlug.of(versionSlug).orElse(null));
    }

    default JavaDependencyOptionalValueBuilder optional() {
      return optional(true);
    }

    default JavaDependencyOptionalValueBuilder addExclusion(GroupId groupId, ArtifactId artifactId) {
      return addExclusion(new DependencyId(groupId, artifactId));
    }
  }
}
