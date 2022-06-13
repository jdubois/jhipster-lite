package tech.jhipster.lite.generator.module.domain.javadependency.command;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import tech.jhipster.lite.common.domain.JHipsterCollections;
import tech.jhipster.lite.error.domain.Assert;

public record JavaDependenciesCommands(Collection<JavaDependencyCommand> commands) {
  public static final JavaDependenciesCommands EMPTY = new JavaDependenciesCommands(List.of());

  public JavaDependenciesCommands(Collection<JavaDependencyCommand> commands) {
    this.commands = JHipsterCollections.immutable(commands);
  }

  public JavaDependenciesCommands merge(JavaDependenciesCommands other) {
    Assert.notNull("other", other);

    List<JavaDependencyCommand> mergedCommands = new ArrayList<>();
    mergedCommands.addAll(commands());
    mergedCommands.addAll(other.commands());

    return new JavaDependenciesCommands(mergedCommands);
  }

  public boolean isEmpty() {
    return get().isEmpty();
  }

  public Collection<JavaDependencyCommand> get() {
    return commands();
  }
}
