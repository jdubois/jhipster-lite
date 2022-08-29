package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;

@UnitTest
class JHipsterModulePropertiesDefinitionControllerTest {

  private static final JHipsterModulePropertiesDefinitionController controller = new JHipsterModulePropertiesDefinitionController(
    JsonHelper.jsonMapper(),
    defaultModuleResource()
  );

  @ParameterizedTest
  @ValueSource(strings = { "dummy", "POST", "PATH", "PUT", "DELETE" })
  void shouldNotHandleRequestForInvalidMethod(String method) {
    assertThatThrownBy(() ->
        controller.handleRequest(new MockHttpServletRequest(method, "/api/modules/dummy"), new MockHttpServletResponse())
      )
      .isExactlyInstanceOf(InvalidProperitesDefinitionQueryException.class);
  }
}
