package tech.jhipster.lite.module.domain.replacement;

import java.util.Collection;
import java.util.function.Consumer;
import tech.jhipster.lite.error.domain.Assert;

public record ContentReplacers(Collection<ContentReplacer> replacers) {
  public ContentReplacers {
    Assert.notNull("replacers", replacers);
  }

  public void forEach(Consumer<ContentReplacer> action) {
    replacers().forEach(action);
  }
}
