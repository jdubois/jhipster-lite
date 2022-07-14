package tech.jhipster.lite.generator.buildtool.gradle.domain;

import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

public class Gradle {

  public static final String GRADLE_NEEDLE_DEPENDENCY = "// jhipster-needle-gradle-add-dependency";

  private static final String IMPLEMENTATION = "implementation(\"";
  private static final String SEPARATOR = ":";
  private static final String END_IMPLEMENTATION = "\")";
  private static final String LF = "\n";

  private Gradle() {}

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
}
