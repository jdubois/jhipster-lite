package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.generator.module.domain.JHipsterModulesFixture.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Pattern;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.module.domain.Indentation;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;

@UnitTest
class MavenCommandHandlerTest {

  @Test
  void shouldNotCreateHandlerFromRandomFile() {
    assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, Paths.get("src/test/resources/projects/empty/.gitkeep")))
      .isExactlyInstanceOf(GeneratorException.class);
  }

  @Test
  void shouldAppendEncodingHeader() {
    Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

    new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetJavaDependencyVersion(springBootVersion()));

    assertThat(content(pom)).startsWith("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
  }

  @Nested
  @DisplayName("Set dependency version")
  class MavenCommandHandlerSetVersionTest {

    @Test
    void shouldNotAddPropertiesToPomWithOnlyRootDefined() {
      Path pom = projectWithPom("src/test/resources/projects/root-only-maven/pom.xml");

      assertThatThrownBy(() -> new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetJavaDependencyVersion(springBootVersion())))
        .isExactlyInstanceOf(InvalidPomException.class);
    }

    @Test
    void shouldAddPropertiesToPomWithoutProperties() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetJavaDependencyVersion(springBootVersion()));

      assertThat(content(pom))
        .contains("  <properties>")
        .contains("    <spring-boot.version>1.2.3</spring-boot.version>")
        .contains("  </properties>");
    }

    @Test
    void shouldAddPropertiesToPomWithProperties() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetJavaDependencyVersion(springBootVersion()));

      assertThat(content(pom)).contains("    <spring-boot.version>1.2.3</spring-boot.version>").doesNotContain(">  ");
    }

    @Test
    void shouldUpdateExistingProperty() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new SetJavaDependencyVersion(new JavaDependencyVersion("jjwt", "0.12.0")));

      assertThat(content(pom))
        .contains("    <jjwt.version>0.12.0</jjwt.version>")
        .doesNotContain("    <jjwt.version>0.11.5</jjwt.version>")
        .doesNotContain(">  ");
    }
  }

  @Nested
  @DisplayName("Remove dependency")
  class MavenCommandHandlerRemoveDependencyTest {

    @Test
    void shouldNotRemoveUnknownDependency() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      assertThatCode(() -> new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependency(jsonWebTokenDependencyId())))
        .doesNotThrowAnyException();
    }

    @Test
    void shouldRemoveDependency() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .doesNotContain("      <groupId>io.jsonwebtoken</groupId>")
        .doesNotContain("      <artifactId>jjwt-api</artifactId>")
        .doesNotContain("      <version>${jjwt.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }

    @Test
    void shouldRemoveDependencyWithFullyMatchingId() {
      Path pom = projectWithPom("src/test/resources/projects/maven-with-multiple-webtoken/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new RemoveJavaDependency(jsonWebTokenDependencyId()));

      assertThat(content(pom))
        .contains("      <groupId>io.jsonwebtoken</groupId>")
        .contains("      <artifactId>jjwt-implementation</artifactId>")
        .contains("      <groupId>io.another</groupId>")
        .contains("      <artifactId>jjwt-api</artifactId>")
        .doesNotContain("      <version>${jjwt.version}</version>")
        .doesNotContain("      <scope>test</scope>")
        .doesNotContain("      <optional>true</optional>");
    }
  }

  @Nested
  @DisplayName("Add dependency")
  class MavenCommandHandlerAddDependencyTest {

    @Test
    void shouldAddDepencyInPomWithoutDepedencies() {
      Path pom = projectWithPom("src/test/resources/projects/empty-maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependency(optionalTestDependency()));

      assertThat(content(pom))
        .contains("  <dependencies>")
        .contains("    <dependency>")
        .contains("      <groupId>org.junit.jupiter</groupId>")
        .contains("      <artifactId>junit-jupiter-engine</artifactId>")
        .contains("      <version>${spring-boot.version}</version>")
        .contains("      <scope>test</scope>")
        .contains("      <optional>true</optional>")
        .contains("  </dependencies>");
    }

    @Test
    void shouldAddTestDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependency(optionalTestDependency()));

      String content = content(pom);
      assertThat(content)
        .contains("      <groupId>org.junit.jupiter</groupId>")
        .contains("      <artifactId>junit-jupiter-engine</artifactId>")
        .contains("      <version>${spring-boot.version}</version>")
        .contains("      <scope>test</scope>")
        .contains("      <optional>true</optional>\n    </dependency>\n  </dependencies>");

      assertThat(Pattern.compile("^ +$", Pattern.MULTILINE).matcher(content).find()).isFalse();
    }

    @Test
    void shouldAddCompileDependencyInPomWithDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains(
          "      <artifactId>logstash-logback-encoder</artifactId>\n" +
          "    </dependency>\n" +
          "\n" +
          "    <dependency>\n" +
          "      <groupId>org.springframework.boot</groupId>\n" +
          "      <artifactId>spring-boot-starter</artifactId>\n" +
          "    </dependency>\n" +
          "\n" +
          "    <dependency>\n" +
          "      <groupId>io.jsonwebtoken</groupId>"
        );
    }

    @Test
    void shouldAddCompileDependencyInPomWithOnlyCompileDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-compile-only/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains("      <groupId>org.springframework.boot</groupId>")
        .contains("      <artifactId>spring-boot-starter</artifactId>\n    </dependency>\n  </dependencies>");
    }

    @Test
    void shouldAddCompileDependencyInPomWithEmptyDependencies() {
      Path pom = projectWithPom("src/test/resources/projects/maven-empty-dependencies/pom.xml");

      new MavenCommandHandler(Indentation.DEFAULT, pom).handle(new AddJavaDependency(defaultVersionDependency()));

      assertThat(content(pom))
        .contains("      <groupId>org.springframework.boot</groupId>")
        .contains("      <artifactId>spring-boot-starter</artifactId>\n    </dependency>\n  </dependencies>");
    }
  }

  private static Path projectWithPom(String sourcePom) {
    Path folder = Paths.get(FileUtils.tmpDirForTest());

    try {
      Files.createDirectories(folder);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    Path pomPath = folder.resolve("pom.xml");
    try {
      Files.copy(Paths.get(sourcePom), pomPath);
    } catch (IOException e) {
      throw new AssertionError(e);
    }

    return pomPath;
  }

  private static String content(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
