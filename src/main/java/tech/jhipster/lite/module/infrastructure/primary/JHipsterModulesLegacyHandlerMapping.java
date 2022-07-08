package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;

@Component
class JHipsterModulesLegacyHandlerMapping extends AbstractUrlHandlerMapping {

  private final Map<String, JHipsterProjectQueryController> controllers;

  public JHipsterModulesLegacyHandlerMapping(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules
  ) {
    this.controllers = buildControllers(json, modulesResources, modules);
  }

  private Map<String, JHipsterProjectQueryController> buildControllers(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules
  ) {
    return modulesResources
      .stream()
      .collect(
        Collectors.toUnmodifiableMap(JHipsterModuleResource::legacyUrl, module -> new JHipsterProjectQueryController(json, module, modules))
      );
  }

  @Override
  protected void initApplicationContext() throws BeansException {
    setOrder(0);

    super.initApplicationContext();
    registerHandlers();
  }

  private void registerHandlers() {
    controllers.forEach(this::registerHandler);
  }
}
