package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterProjectFolderFactory;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

class JHipsterModuleApplicationController extends JHipsterModuleController {

  private final ObjectMapper json;
  private final JHipsterProjectFolderFactory jHipsterProjectFolderFactory;

  public JHipsterModuleApplicationController(
    ObjectMapper json,
    JHipsterModuleResource module,
    JHipsterModulesApplicationService modules,
    JHipsterProjectFolderFactory jHipsterProjectFolderFactory
  ) {
    super(module, modules);
    Assert.notNull("json", json);

    this.json = json;
    this.jHipsterProjectFolderFactory = jHipsterProjectFolderFactory;
  }

  @Override
  protected JHipsterModuleProperties readProperties(HttpServletRequest request) throws IOException {
    return json
      .readValue(StreamUtils.copyToByteArray(request.getInputStream()), RestJHipsterModuleProperties.class)
      .toDomain(jHipsterProjectFolderFactory);
  }
}
