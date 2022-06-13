package tech.jhipster.lite.generator.module.domain.javadependency;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import java.util.List;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;

@UnitTest
class JavaDependencyTest {

  @Test
  void shouldAddUnknownMinimalDependency() {
    JavaDependenciesCommands commands = defaultVersionDependency()
      .changeCommands(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(commands.get()).containsExactly(new AddJavaDependency(defaultVersionDependency()));
  }

  @Test
  void shouldAddUnknownFullDependency() {
    JavaDependenciesCommands commands = optionalTestDependency()
      .changeCommands(currentJavaDependenciesVersion(), ProjectJavaDependencies.EMPTY);

    assertThat(commands.get())
      .containsExactly(new SetJavaDependencyVersion(springBootVersion()), new AddJavaDependency(optionalTestDependency()));
  }

  @Test
  void shouldNotUpdateExistingOptionalTestDependency() {
    JavaDependenciesCommands commands = optionalTestDependency()
      .changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies());

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldNotUpdateExistingDefaultVersionDependency() {
    ProjectJavaDependencies projectJavaDependencies = new ProjectJavaDependencies(
      projectVersions(),
      new JavaDependencies(List.of(defaultVersionDependency()))
    );
    JavaDependenciesCommands commands = defaultVersionDependency()
      .changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies);

    assertThat(commands.get()).isEmpty();
  }

  @Test
  void shouldUpgradeDepencencyOptionality() {
    ProjectJavaDependencies projectJavaDependencies = new ProjectJavaDependencies(
      projectVersions(),
      new JavaDependencies(List.of(optionalSpringBootDependency()))
    );

    JavaDependenciesCommands commands = defaultVersionDependency()
      .changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies);

    assertThat(commands.get())
      .containsExactly(new RemoveJavaDependency(optionalSpringBootDependency().id()), new AddJavaDependency(defaultVersionDependency()));
  }

  @Test
  void shouldNotDowngradeDepencencyOptionality() {
    ProjectJavaDependencies projectJavaDependencies = new ProjectJavaDependencies(
      projectVersions(),
      new JavaDependencies(List.of(defaultVersionDependency()))
    );

    JavaDependenciesCommands commands = optionalSpringBootDependency()
      .changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies);

    assertThat(commands.get()).isEmpty();
  }

  private JavaDependency optionalSpringBootDependency() {
    return javaDependency().groupId("org.springframework.boot").artifactId("spring-boot-starter").optional().build();
  }

  @Test
  void shouldUpdateDependencyVersion() {
    JavaDependencyVersion updatedVersion = new JavaDependencyVersion("spring-boot", "1.2.4");
    CurrentJavaDependenciesVersions currentVersions = new CurrentJavaDependenciesVersions(List.of(updatedVersion));

    JavaDependenciesCommands commands = optionalTestDependency().changeCommands(currentVersions, projectJavaDependencies());

    assertThat(commands.get()).containsExactly(new SetJavaDependencyVersion(updatedVersion));
  }

  @Test
  void shouldUpgradeDependencyScopeAndOptionality() {
    JavaDependency upgraded = javaDependency()
      .groupId("org.junit.jupiter")
      .artifactId("junit-jupiter-engine")
      .versionSlug("spring-boot")
      .build();

    JavaDependenciesCommands commands = upgraded.changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies());

    assertThat(commands.get()).containsExactly(new RemoveJavaDependency(upgraded.id()), new AddJavaDependency(upgraded));
  }

  @Test
  void shouldKeepVersionFromNewDependency() {
    JavaDependency upgraded = optionalTestDependency();

    JavaDependenciesCommands commands = upgraded.changeCommands(currentJavaDependenciesVersion(), projectDependenciesWithoutJunitVersion());

    assertThat(commands.get()).containsExactly(new RemoveJavaDependency(upgraded.id()), new AddJavaDependency(upgraded));
  }

  @Test
  void shouldUpdateVersionSlug() {
    JavaDependency upgraded = optionalTestDependencyBuilder().versionSlug("updated-junit-jupiter").build();
    JavaDependencyVersion updatedJunitVersion = new JavaDependencyVersion("updated-junit-jupiter", "1.2.4");
    CurrentJavaDependenciesVersions currentVersions = new CurrentJavaDependenciesVersions(
      List.of(springBootVersion(), updatedJunitVersion)
    );

    JavaDependenciesCommands commands = upgraded.changeCommands(currentVersions, projectDependenciesWithoutJunitVersion());

    assertThat(commands.get())
      .containsExactly(
        new SetJavaDependencyVersion(updatedJunitVersion),
        new RemoveJavaDependency(upgraded.id()),
        new AddJavaDependency(upgraded)
      );
  }

  private ProjectJavaDependencies projectDependenciesWithoutJunitVersion() {
    return new ProjectJavaDependencies(projectVersions(), noJunitVersionInCurrentProject());
  }

  private JavaDependencies noJunitVersionInCurrentProject() {
    return new JavaDependencies(List.of(junitWithoutVersion()));
  }

  @Test
  void shouldKeepVersionFromProject() {
    JavaDependency upgraded = junitWithoutVersion();

    JavaDependenciesCommands commands = upgraded.changeCommands(currentJavaDependenciesVersion(), projectJavaDependencies());

    assertThat(commands.get()).isEmpty();
  }

  private JavaDependency junitWithoutVersion() {
    return optionalTestDependencyBuilder().versionSlug((String) null).build();
  }

  private ProjectJavaDependencies projectJavaDependencies() {
    return new ProjectJavaDependencies(projectVersions(), projectDependencies());
  }

  private JavaDependenciesVersions projectVersions() {
    return new JavaDependenciesVersions(List.of(springBootVersion()));
  }

  private JavaDependencies projectDependencies() {
    return new JavaDependencies(List.of(optionalTestDependency()));
  }
}
