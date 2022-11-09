package tech.jhipster.lite.module.domain.javabuildplugin;

import java.util.Optional;
import tech.jhipster.lite.module.domain.javabuild.ArtifactId;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;

public class JavaBuildPlugin {

  private final DependencyId dependencyId;
  private final Optional<VersionSlug> versionSlug;
  private final Optional<JavaBuildPluginAdditionalElements> additionalElements;

  private JavaBuildPlugin(JavaBuildPluginBuilder builder) {
    dependencyId = new DependencyId(builder.groupId, builder.artifactId, Optional.empty());
    versionSlug = Optional.ofNullable(builder.versionSlug);
    additionalElements = Optional.ofNullable(builder.additionalElements);
  }

  public static JavaBuildPluginGroupIdBuilder builder() {
    return new JavaBuildPluginBuilder();
  }

  public Optional<VersionSlug> versionSlug() {
    return versionSlug;
  }

  public Optional<JavaBuildPluginAdditionalElements> additionalElements() {
    return additionalElements;
  }

  public DependencyId dependencyId() {
    return dependencyId;
  }

  public static class JavaBuildPluginBuilder
    implements JavaBuildPluginGroupIdBuilder, JavaBuildPluginArtifactIdBuilder, JavaBuildPluginOptionalBuilder {

    private GroupId groupId;
    private ArtifactId artifactId;
    private VersionSlug versionSlug;
    private JavaBuildPluginAdditionalElements additionalElements;

    private JavaBuildPluginBuilder() {}

    @Override
    public JavaBuildPluginArtifactIdBuilder groupId(GroupId groupId) {
      this.groupId = groupId;

      return this;
    }

    @Override
    public JavaBuildPluginOptionalBuilder artifactId(ArtifactId artifactId) {
      this.artifactId = artifactId;

      return this;
    }

    @Override
    public JavaBuildPluginOptionalBuilder versionSlug(VersionSlug versionSlug) {
      this.versionSlug = versionSlug;

      return this;
    }

    @Override
    public JavaBuildPluginOptionalBuilder additionalElements(JavaBuildPluginAdditionalElements additionalElements) {
      this.additionalElements = additionalElements;

      return this;
    }

    @Override
    public JavaBuildPlugin build() {
      return new JavaBuildPlugin(this);
    }
  }

  public interface JavaBuildPluginGroupIdBuilder {
    JavaBuildPluginArtifactIdBuilder groupId(GroupId groupId);

    default JavaBuildPluginArtifactIdBuilder groupId(String groupId) {
      return groupId(new GroupId(groupId));
    }
  }

  public interface JavaBuildPluginArtifactIdBuilder {
    JavaBuildPluginOptionalBuilder artifactId(ArtifactId artifactId);

    default JavaBuildPluginOptionalBuilder artifactId(String artifactId) {
      return artifactId(new ArtifactId(artifactId));
    }
  }

  public interface JavaBuildPluginOptionalBuilder {
    JavaBuildPluginOptionalBuilder versionSlug(VersionSlug slug);

    JavaBuildPluginOptionalBuilder additionalElements(JavaBuildPluginAdditionalElements additionalElements);

    JavaBuildPlugin build();

    default JavaBuildPluginOptionalBuilder versionSlug(String slug) {
      return versionSlug(new VersionSlug(slug));
    }

    default JavaBuildPluginOptionalBuilder additionalElements(String additionalElements) {
      return additionalElements(new JavaBuildPluginAdditionalElements(additionalElements));
    }
  }
}
