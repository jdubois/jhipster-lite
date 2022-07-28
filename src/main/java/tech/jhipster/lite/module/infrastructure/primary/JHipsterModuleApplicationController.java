package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.StreamUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

class JHipsterModuleApplicationController extends JHipsterModuleController {

  private final ObjectMapper json;
  private final ProjectFolder jHipsterProjectFolderFactory;

  public JHipsterModuleApplicationController(
    ObjectMapper json,
    JHipsterModuleResource module,
    JHipsterModulesApplicationService modules,
    ProjectFolder jHipsterProjectFolderFactory
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
