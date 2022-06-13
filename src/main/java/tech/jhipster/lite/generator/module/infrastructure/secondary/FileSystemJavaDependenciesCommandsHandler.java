package tech.jhipster.lite.generator.module.infrastructure.secondary;

import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.stereotype.Service;
import tech.jhipster.lite.common.domain.Generated;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.domain.Indentation;
import tech.jhipster.lite.generator.module.domain.javadependency.command.AddJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependenciesCommands;
import tech.jhipster.lite.generator.module.domain.javadependency.command.JavaDependencyCommand;
import tech.jhipster.lite.generator.module.domain.javadependency.command.RemoveJavaDependency;
import tech.jhipster.lite.generator.module.domain.javadependency.command.SetJavaDependencyVersion;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterProjectFolder;

@Service
class FileSystemJavaDependenciesCommandsHandler {

  public void handle(Indentation indentation, JHipsterProjectFolder projectFolder, JavaDependenciesCommands commands) {
    Assert.notNull("indentiation", indentation);
    Assert.notNull("projectFolder", projectFolder);
    Assert.notNull("commands", commands);

    if (commands.isEmpty()) {
      return;
    }

    Path pomPath = projectFolder.filePath("pom.xml");

    if (Files.notExists(pomPath)) {
      throw new MissingPomException(projectFolder);
    }

    MavenCommandHandler handler = new MavenCommandHandler(indentation, pomPath);

    commands.get().forEach(command -> handle(handler, command));
  }

  @Generated
  private void handle(MavenCommandHandler handler, JavaDependencyCommand command) {
    switch (command.type()) {
      case SET_VERSION -> handler.handle((SetJavaDependencyVersion) command);
      case REMOVE -> handler.handle((RemoveJavaDependency) command);
      case ADD -> handler.handle((AddJavaDependency) command);
    }
  }
}
