package tech.jhipster.lite.generator.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

@Component
class JHipsterModulesPropertiesDefinitionHandlerMapping extends AbstractUrlHandlerMapping {

  private final Map<String, JHipsterModulePropertiesDefinitionController> controllers;

  public JHipsterModulesPropertiesDefinitionHandlerMapping(ObjectMapper json, JHipsterModulesResources modulesResources) {
    controllers = buildControllers(json, modulesResources);
  }

  private Map<String, JHipsterModulePropertiesDefinitionController> buildControllers(
    ObjectMapper json,
    JHipsterModulesResources modulesResources
  ) {
    return modulesResources.stream().collect(Collectors.toUnmodifiableMap(toModuleUrl(), toPropertiesDefinitionController(json)));
  }

  private Function<JHipsterModuleResource, String> toModuleUrl() {
    return JHipsterModuleResource::moduleUrl;
  }

  private Function<JHipsterModuleResource, JHipsterModulePropertiesDefinitionController> toPropertiesDefinitionController(
    ObjectMapper json
  ) {
    return moduleResource -> new JHipsterModulePropertiesDefinitionController(json, moduleResource);
  }

  @Override
  protected void initApplicationContext() throws BeansException {
    setOrder(1);

    super.initApplicationContext();
    registerHandlers();
  }

  private void registerHandlers() {
    controllers.forEach(this::registerHandler);
  }
}
