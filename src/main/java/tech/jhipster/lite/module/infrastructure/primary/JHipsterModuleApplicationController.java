package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.resource.JHipsterModuleResource;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

class JHipsterModuleApplicationController implements Controller {

  private final JHipsterModulesApplicationService modules;
  private final JHipsterModuleSlug slug;
  private final JHipsterModuleFactory factory;
  private final ObjectMapper json;
  private final ProjectFolder jHipsterProjectFolderFactory;

  public JHipsterModuleApplicationController(
    ObjectMapper json,
    JHipsterModuleResource module,
    JHipsterModulesApplicationService modules,
    ProjectFolder jHipsterProjectFolderFactory
  ) {
    Assert.notNull("module", module);
    Assert.notNull("modules", modules);
    Assert.notNull("json", json);

    this.modules = modules;
    this.json = json;
    this.jHipsterProjectFolderFactory = jHipsterProjectFolderFactory;

    slug = module.slug();
    factory = module.factory();
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
    if (!HttpMethod.POST.matches(request.getMethod())) {
      throw InvalidModuleQueryException.unsupportedMethod();
    }

    try {
      applyModule(request, response);
    } catch (IOException e) {
      throw InvalidModuleQueryException.unreadablePayload(e);
    }

    return null;
  }

  private void applyModule(HttpServletRequest request, HttpServletResponse response) throws IOException {
    try {
      JHipsterModuleProperties properties = readProperties(request);

      modules.apply(new JHipsterModuleToApply(slug, factory.create(properties)));
    } catch (InvalidProjectFolderException e) {
      response.getWriter().print("Project folder is not valid");
      response.setStatus(400);
    }
  }

  private JHipsterModuleProperties readProperties(HttpServletRequest request) throws IOException {
    return json
      .readValue(StreamUtils.copyToByteArray(request.getInputStream()), RestJHipsterModuleProperties.class)
      .toDomain(jHipsterProjectFolderFactory);
  }
}
