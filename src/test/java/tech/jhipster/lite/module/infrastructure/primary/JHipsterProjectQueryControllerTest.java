package tech.jhipster.lite.module.infrastructure.primary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.module.infrastructure.primary.JHipsterModulesResourceFixture.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import tech.jhipster.lite.JsonHelper;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;

@UnitTest
class JHipsterProjectQueryControllerTest {

  private static final JHipsterProjectQueryController controller = new JHipsterProjectQueryController(
    JsonHelper.jsonMapper(),
    defaultModuleResource(),
    mock(JHipsterModulesApplicationService.class)
  );

  @ParameterizedTest
  @ValueSource(strings = { "dummy", "GET", "PATH", "PUT", "DELETE" })
  void shouldNotHandleRequestForInvalidMethod(String method) {
    assertThatThrownBy(() -> controller.handleRequest(new MockHttpServletRequest(method, "/api/dummy"), new MockHttpServletResponse()))
      .isExactlyInstanceOf(InvalidModuleQueryException.class);
  }

  @Test
  void shouldNotHandleUnreadableRequest() {
    MockHttpServletRequest request = new MockHttpServletRequest("POST", "/api/dummy");
    request.setContent("{".getBytes());

    assertThatThrownBy(() -> controller.handleRequest(request, new MockHttpServletResponse()))
      .isExactlyInstanceOf(InvalidModuleQueryException.class);
  }
}
