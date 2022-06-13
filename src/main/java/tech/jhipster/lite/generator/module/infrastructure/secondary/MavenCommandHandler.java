package tech.jhipster.lite.generator.module.infrastructure.secondary;

import static org.joox.JOOX.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import org.joox.JOOX;
import org.joox.Match;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import tech.jhipster.lite.common.domain.Enums;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.module.domain.Indentation;
import tech.jhipster.lite.generator.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.generator.module.domain.javadependency.JavaDependencyScope;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;

class MavenCommandHandler {

  private static final Pattern SPACES_ONLY_LINE = Pattern.compile("^\\s+$", Pattern.MULTILINE);
  private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n";
  private static final String COMMAND = "command";
  private static final String GROUP_ID = "groupId";
  private static final String ARTIFACT_ID = "artifactId";
  private static final String VERSION = "version";
  private static final String[] PROPERTIES_ANCHOR = new String[] { "parent", "packaging", "description", "name", VERSION, ARTIFACT_ID };

  private static final String[] DEPENDENCIES_ANCHOR = new String[] {
    "dependencyManagement",
    "properties",
    "parent",
    "packaging",
    "description",
    "name",
    VERSION,
    ARTIFACT_ID,
  };
  private static final String BREAK = "\n";

  private final Indentation indentation;
  private final Path pomPath;
  private final Match document;

  MavenCommandHandler(Indentation indentation, Path pomPath) {
    Assert.notNull("indentiation", indentation);
    Assert.notNull("pomPath", pomPath);

    this.indentation = indentation;
    this.pomPath = pomPath;
    document = readDocument(pomPath);
  }

  private Match readDocument(Path pomPath) {
    try (InputStream input = Files.newInputStream(pomPath)) {
      return $(input);
    } catch (IOException | SAXException e) {
      throw new GeneratorException("Error reading pom content: " + e.getMessage(), e);
    }
  }

  public void handle(SetJavaDependencyVersion command) {
    Assert.notNull(COMMAND, command);

    Match properties = document.find("project > properties");
    if (properties.isEmpty()) {
      appendProperties(command);
    } else {
      appendPropertyLine(properties, command);
    }

    writePom();
  }

  private void appendProperties(SetJavaDependencyVersion command) {
    Match properties = $("properties").append(BREAK).append(indentation.spaces());

    appendPropertyLine(properties, command);

    findFirst(PROPERTIES_ANCHOR).after(properties);

    document.find("project properties").before(BREAK).before(BREAK).before(indentation.spaces());
  }

  private void appendPropertyLine(Match properties, SetJavaDependencyVersion command) {
    Match propertyNode = properties.children().filter(command.property());

    if (propertyNode.isNotEmpty()) {
      propertyNode.text(command.dependencyVersion());
    } else {
      properties
        .append(indentation.spaces())
        .append($(command.property(), command.dependencyVersion()))
        .append(BREAK)
        .append(indentation.spaces());
    }
  }

  public void handle(RemoveJavaDependency command) {
    Assert.notNull(COMMAND, command);

    document
      .find("project > dependencies > dependency")
      .each()
      .stream()
      .filter(dependencyMatch(command.dependency()))
      .forEach(Match::remove);

    writePom();
  }

  public void handle(AddJavaDependency command) {
    Assert.notNull(COMMAND, command);

    Match dependencies = document.find("project > dependencies");
    if (dependencies.isEmpty()) {
      appendDependencies(command);
    } else {
      appendDependency(command, dependencies);
    }

    writePom();
  }

  private void appendDependencies(AddJavaDependency command) {
    Match dependencies = $("dependencies")
      .append(BREAK)
      .append(indentation.times(2))
      .append(dependencyNode(command))
      .append(BREAK)
      .append(indentation.spaces());

    findFirst(DEPENDENCIES_ANCHOR).after(dependencies);

    document.find("project dependencies").before(BREAK).before(BREAK).before(indentation.spaces());
  }

  private void appendDependency(AddJavaDependency command, Match dependencies) {
    if (command.scope() == JavaDependencyScope.TEST) {
      appendDependencyInLastPosition(command, dependencies);
    } else {
      appendNotTestDependency(command, dependencies);
    }
  }

  private void appendNotTestDependency(AddJavaDependency command, Match dependencies) {
    dependencies
      .children()
      .each()
      .stream()
      .filter(testDependency())
      .findFirst()
      .ifPresentOrElse(appendBeforeFirstTestDependency(command), () -> appendDependencyInLastPosition(command, dependencies));
  }

  private Consumer<Match> appendBeforeFirstTestDependency(AddJavaDependency command) {
    return firstTestDependency -> {
      Match dependencyNode = dependencyNode(command);
      firstTestDependency.before(dependencyNode);

      document
        .find("project > dependencies > dependency")
        .each()
        .stream()
        .filter(dependencyMatch(command.dependencyId()))
        .findFirst()
        .ifPresent(node -> node.after(indentation.times(2)).after(BREAK).after(BREAK));
    };
  }

  private Predicate<Match> dependencyMatch(DependencyId dependency) {
    return dependencyNode -> {
      boolean sameGroupId = dependencyNode.child(GROUP_ID).text().equals(dependency.groupId().get());
      boolean sameArtifactId = dependencyNode.child(ARTIFACT_ID).text().equals(dependency.artifactId().get());

      return sameGroupId && sameArtifactId;
    };
  }

  private Predicate<Match> testDependency() {
    return dependency -> "test".equals(dependency.child("scope").text());
  }

  private void appendDependencyInLastPosition(AddJavaDependency command, Match dependencies) {
    dependencies.append(BREAK).append(indentation.times(2)).append(dependencyNode(command)).append(BREAK).append(indentation.spaces());
  }

  private Match dependencyNode(AddJavaDependency command) {
    Match dependency = buildDependencyNode(command);

    appendVersion(command, dependency);
    appendScope(command, dependency);
    appendOptional(command, dependency);

    dependency.append(BREAK).append(indentation.times(2));

    return dependency;
  }

  private Match buildDependencyNode(AddJavaDependency command) {
    return $("dependency")
      .append(BREAK)
      .append(indentation.times(3))
      .append($(GROUP_ID, command.groupId().get()))
      .append(BREAK)
      .append(indentation.times(3))
      .append($(ARTIFACT_ID, command.artifactId().get()));
  }

  private void appendVersion(AddJavaDependency command, Match dependency) {
    command
      .version()
      .ifPresent(version -> dependency.append(BREAK).append(indentation.times(3)).append($(VERSION, version.mavenVariable())));
  }

  private void appendScope(AddJavaDependency command, Match dependency) {
    if (command.scope() != JavaDependencyScope.COMPILE) {
      dependency.append(BREAK).append(indentation.times(3)).append($("scope", Enums.map(command.scope(), MavenScope.class).key()));
    }
  }

  private void appendOptional(AddJavaDependency command, Match dependency) {
    if (command.optional()) {
      dependency.append(BREAK).append(indentation.times(3)).append($("optional", "true"));
    }
  }

  @Generated
  private void writePom() {
    try (Writer writer = Files.newBufferedWriter(pomPath, StandardCharsets.UTF_8)) {
      writer.write(HEADER);

      for (Element e : document) {
        String element = JOOX.$(e).toString().replace("\r\n", "\n");

        element = SPACES_ONLY_LINE.matcher(element).replaceAll("");

        writer.write(element);
      }
    } catch (IOException e) {
      throw new GeneratorException("Error writing pom: " + e.getMessage(), e);
    }
  }

  private Match findFirst(String... elements) {
    return Stream.of(elements).map(document::find).filter(Match::isNotEmpty).findFirst().orElseThrow(InvalidPomException::new);
  }
}
