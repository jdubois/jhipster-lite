package tech.jhipster.lite.generator.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.generator.module.domain.properties.JHipsterModuleProperties;

class JHipsterModuleApplicationController extends JHipsterModuleController {

  private final ObjectMapper json;

  public JHipsterModuleApplicationController(ObjectMapper json, JHipsterModuleResource module, JHipsterModulesApplicationService modules) {
    super(module, modules);
    Assert.notNull("json", json);

    this.json = json;
  }

  @Override
  protected JHipsterModuleProperties readProperties(HttpServletRequest request) throws IOException {
    return json.readValue(StreamUtils.copyToByteArray(request.getInputStream()), RestJHipsterModuleProperties.class).toDomain();
  }
}
