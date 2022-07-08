package tech.jhipster.lite.module.infrastructure.primary;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModuleFactory;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

abstract class JHipsterModuleController implements Controller {

  private final JHipsterModulesApplicationService modules;
  private final JHipsterModuleSlug slug;
  private final JHipsterModuleFactory factory;

  protected JHipsterModuleController(JHipsterModuleResource module, JHipsterModulesApplicationService modules) {
    Assert.notNull("module", module);
    Assert.notNull("modules", modules);

    this.modules = modules;
    slug = module.slug();
    factory = module.factory();
  }

  @Override
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
    if (!HttpMethod.POST.matches(request.getMethod())) {
      throw InvalidModuleQueryException.unsupportedMethod();
    }

    try {
      JHipsterModuleProperties properties = readProperties(request);

      modules.apply(new JHipsterModuleToApply(properties, slug, factory.create(properties)));
    } catch (IOException e) {
      throw InvalidModuleQueryException.unreadablePayload(e);
    }

    return null;
  }

  protected abstract JHipsterModuleProperties readProperties(HttpServletRequest request) throws IOException;
}
