package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import tech.jhipster.lite.error.domain.Assert;

class JHipsterModulePropertiesDefinitionController implements Controller {

  private final ObjectMapper json;
  private final Map<String, Object> propertiesDefinition;

  @SuppressWarnings("unchecked")
  public JHipsterModulePropertiesDefinitionController(ObjectMapper json, JHipsterModuleResource module) {
    Assert.notNull("json", json);
    Assert.notNull("module", module);

    this.json = json;
    propertiesDefinition = json.convertValue(RestJHipsterModulePropertiesDefinition.from(module.propertiesDefinition()), Map.class);
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
    if (!HttpMethod.GET.matches(request.getMethod())) {
      throw new InvalidProperitesDefinitionQueryException();
    }

    return new ModelAndView(new MappingJackson2JsonView(json), propertiesDefinition);
  }
}
