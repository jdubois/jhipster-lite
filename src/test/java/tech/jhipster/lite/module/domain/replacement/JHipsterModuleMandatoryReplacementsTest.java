package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.domain.JHipsterModule.JHipsterModuleBuilder;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

@UnitTest
class JHipsterModuleMandatoryReplacementsTest {

  @Test
  void shouldNotApplyReplacementOnUnknownFile() {
    assertThatThrownBy(() -> replaceIn("unknown")).isExactlyInstanceOf(ReplacementErrorException.class);
  }

  @Test
  void shouldNotApplyReplacementOnUnknownCurrentValue() {
    assertThatThrownBy(() -> replaceIn("maven/pom.xml")).isExactlyInstanceOf(UnknownCurrentValueException.class);
  }

  @Test
  void shouldApplyCorrectTextMatcherPlacement() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestUtils.copyPomXml(project);

    JHipsterProjectFolder folder = new JHipsterProjectFolder(project.getFolder());
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModuleProperties.defaultProperties(folder))
      .mandatoryReplacements()
      .in("pom.xml")
      .add(justBefore(new TextMatcher("</dependencies>")), "Before")
      .add(justAfter(new TextMatcher("</dependencies>")), "aFter")
      .add(justLineBefore(new TextMatcher("<modelVersion>4.0.0</modelVersion>")), "BeforeLine")
      .add(justLineAfter(new TextMatcher("<modelVersion>4.0.0</modelVersion>")), "AfterLine")
      .and()
      .and();

    assertThatModule(module.build())
      .createFile("pom.xml")
      .containing("Before</dependencies>")
      .containing("</dependencies>aFter")
      .containing("BeforeLine\n<modelVersion>4.0.0</modelVersion>")
      .containing("<modelVersion>4.0.0</modelVersion>\nAfterLine");
  }

  @Test
  void shouldApplyCorrectRegexMatcherPlacement() {
    Project project = tmpProjectWithPackageJsonComplete();
    TestUtils.copyPomXml(project);

    JHipsterProjectFolder folder = new JHipsterProjectFolder(project.getFolder());
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModuleProperties.defaultProperties(folder))
      .mandatoryReplacements()
      .in("pom.xml")
      .add(justBefore(new RegexMatcher("</dependencies>")), "Before")
      .add(justAfter(new RegexMatcher("</dependencies>")), "aFter")
      .add(justLineBefore(new RegexMatcher("<modelVersion>4.0.0</modelVersion>")), "BeforeLine")
      .add(justLineAfter(new RegexMatcher("<modelVersion>4.0.0</modelVersion>")), "AfterLine")
      .and()
      .and();

    assertThatModule(module.build())
      .createFile("pom.xml")
      .containing("Before</dependencies>")
      .containing("</dependencies>aFter")
      .containing("BeforeLine\n<modelVersion>4.0.0</modelVersion>")
      .containing("<modelVersion>4.0.0</modelVersion>\nAfterLine");
  }

  private static void replaceIn(String file) {
    JHipsterProjectFolder folder = new JHipsterProjectFolder("src/test/resources/projects");
    JHipsterModuleBuilder module = moduleBuilder(JHipsterModuleProperties.defaultProperties(folder));

    JHipsterModuleMandatoryReplacements.builder(module).in(file).add(new TextMatcher("old"), "new").and().build().apply(folder);
  }
}
