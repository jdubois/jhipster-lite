package tech.jhipster.lite.module.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class ArgumentsReplacerTest {

  @Test
  void shouldNotReplaceArgumentsInNullMessage() {
    assertThat(ArgumentsReplacer.replaceArguments(null, Map.of("key", "value"))).isNull();
  }

  @Test
  void shouldNotReplaceUnknownArguments() {
    assertThat(ArgumentsReplacer.replaceArguments("Hey {{ user }}", null)).isEqualTo("Hey {{ user }}");
  }

  @Test
  void shouldReplaceKnownArguments() {
    assertThat(ArgumentsReplacer.replaceArguments("Hey {{ user }}, how's {{ friend }} doing? Say {{user}}", Map.of("user", "Joe")))
      .isEqualTo("Hey Joe, how's {{ friend }} doing? Say Joe");
  }

  @Test
  void shouldReplaceObjectArguments() {
    Map<String, Object> arguments = new HashMap<>();
    arguments.put("number", 42);
    arguments.put("null", null);

    assertThat(ArgumentsReplacer.replaceArguments("Hey {{ number }}, how's {{ null }} doing?", arguments))
      .isEqualTo("Hey 42, how's {{ null }} doing?");
  }
}
