package tech.jhipster.lite.generator.buildtool.gradle.domain;

import java.util.Map;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.buildtool.generic.domain.Repository;

public class Gradle {

  public static final String GRADLE_NEEDLE_DEPENDENCY = "// jhipster-needle-gradle-add-dependency";
  public static final String GRADLE_NEEDLE_REPOSITORY = "// jhipster-needle-gradle-repository";

  public static final String IMPLEMENTATION = "implementation(\"";
  public static final String SEPARATOR = ":";
  public static final String END_IMPLEMENTATION = "\")";
  public static final String LF = "\n";

  private Gradle() {}

  public static Map<String, String> gradleWrapper() {
    // prettier-ignore
    return Map.of(
      "gradlew", ".",
      "gradlew.bat", ".",
      "gradle-wrapper.jar", "gradle/wrapper",
      "gradle-wrapper.properties", "gradle/wrapper"
    );
  }

  public static String getDependency(Dependency dependency) {
    return new StringBuilder()
      .append(IMPLEMENTATION)
      .append(dependency.getGroupId())
      .append(SEPARATOR)
      .append(dependency.getArtifactId())
      .append(END_IMPLEMENTATION)
      .append(LF)
      .toString()
      .indent(2);
  }

  public static String getDependencyWithNeedle(Dependency dependency) {
    return getDependency(dependency) + GRADLE_NEEDLE_DEPENDENCY.indent(2);
  }

  public static String getRepositoryString(Repository repository) {
    return """
        maven {
          url = uri("%s")
        }
        // jhipster-needle-gradle-repository
      """.formatted(
        repository.getUrl()
      );
  }
}
