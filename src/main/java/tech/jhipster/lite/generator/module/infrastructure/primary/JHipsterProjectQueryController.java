package tech.jhipster.lite.generator.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.generator.project.infrastructure.primary.dto.ProjectDTO;

class JHipsterProjectQueryController extends JHipsterModuleController {

  private final ObjectMapper json;

  public JHipsterProjectQueryController(ObjectMapper json, JHipsterModuleResource module, JHipsterModulesApplicationService modules) {
    super(module, modules);
    Assert.notNull("json", json);

    this.json = json;
  }

  @Override
  protected JHipsterModuleProperties readProperties(HttpServletRequest request) throws IOException {
    return json.readValue(StreamUtils.copyToByteArray(request.getInputStream()), ProjectDTO.class).toModuleProperties();
  }
}
