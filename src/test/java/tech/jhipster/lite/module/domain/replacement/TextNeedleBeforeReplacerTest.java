package tech.jhipster.lite.module.domain.replacement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class TextNeedleBeforeReplacerTest {

  @Test
  void shouldNotMatchUnknownText() {
    assertThat(new TextNeedleBeforeReplacer("unknown").notMatchIn("content")).isTrue();
  }

  @Test
  void shouldMatchKnownText() {
    assertThat(new TextNeedleBeforeReplacer("known").notMatchIn("unknown")).isFalse();
  }

  @Test
  void shouldNotReplaceUnknownNeedle() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer("<!-- needle !-->");

    String updatedContent = replacer.replacer().apply("<root />", "<element />");

    assertThat(updatedContent).isEqualTo("<root />");
  }

  @Test
  void shouldInsertTextLineBeforeFirstLineNeedle() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer("<!-- needle !-->");

    String updatedContent = replacer.replacer().apply("""
            <!-- needle !-->
            """, "<element />");

    assertThat(updatedContent).isEqualTo("""
        <element />
        <!-- needle !-->
        """);
  }

  @Test
  void shouldInsertTextLineBeforeNeedleLine() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer("<!-- needle !-->");

    String updatedContent = replacer
      .replacer()
      .apply("""
            <root>
            <!-- needle !-->
            </root>
            """, "<element />");

    assertThat(updatedContent).isEqualTo("""
        <root>
        <element />
        <!-- needle !-->
        </root>
        """);
  }

  @Test
  void shouldInsertTextLineBeforeNeedleLinePart() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer("<!-- needle !-->");

    String updatedContent = replacer
      .replacer()
      .apply("""
            <root>
              <!-- needle !-->
              
            </root>
            """, "<element />");

    assertThat(updatedContent)
      .isEqualTo("""
        <root>
        <element />
          <!-- needle !-->
          
        </root>
        """);
  }

  @Test
  void shouldReplaceMultipleNeedles() {
    TextNeedleBeforeReplacer replacer = new TextNeedleBeforeReplacer("<!-- needle !-->");

    String updatedContent = replacer
      .replacer()
      .apply(
        """
            <root>
              <!-- needle !-->
              
              
              <!-- needle !-->
              <!-- needle !--> with trailling text
            </root>
            """,
        "<element />"
      );

    assertThat(updatedContent)
      .isEqualTo(
        """
        <root>
        <element />
          <!-- needle !-->
          
          
        <element />
          <!-- needle !-->
        <element />
          <!-- needle !--> with trailling text
        </root>
        """
      );
  }

  @Test
  void shouldGetTextAsMatcher() {
    assertThat(new TextNeedleBeforeReplacer("known").searchMatcher()).isEqualTo("known");
  }
}
