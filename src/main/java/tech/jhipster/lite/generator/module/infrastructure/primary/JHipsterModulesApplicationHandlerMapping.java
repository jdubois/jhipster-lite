package tech.jhipster.lite.generator.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import tech.jhipster.lite.generator.module.application.JHipsterModulesApplicationService;

@Component
class JHipsterModulesApplicationHandlerMapping extends AbstractUrlHandlerMapping {

  private final Map<String, JHipsterModuleApplicationController> controllers;

  public JHipsterModulesApplicationHandlerMapping(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules
  ) {
    controllers = buildControllers(json, modulesResources, modules);
  }

  private Map<String, JHipsterModuleApplicationController> buildControllers(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules
  ) {
    return modulesResources.stream().collect(Collectors.toUnmodifiableMap(toApplyUrl(), toApplyerController(json, modules)));
  }

  private Function<JHipsterModuleResource, String> toApplyUrl() {
    return moduleResource -> moduleResource.moduleUrl() + "/apply";
  }

  private Function<JHipsterModuleResource, JHipsterModuleApplicationController> toApplyerController(
    ObjectMapper json,
    JHipsterModulesApplicationService modules
  ) {
    return module -> new JHipsterModuleApplicationController(json, module, modules);
  }

  @Override
  protected void initApplicationContext() throws BeansException {
    setOrder(2);

    super.initApplicationContext();
    registerHandlers();
  }

  private void registerHandlers() {
    controllers.forEach(this::registerHandler);
  }
}
