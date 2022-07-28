package tech.jhipster.lite.module.infrastructure.primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.projectfolder.domain.ProjectFolder;

@Component
class JHipsterModulesPatchApplicationHandlerMapping extends AbstractUrlHandlerMapping {

  private final Map<String, JHipsterModuleApplicationController> controllers;

  public JHipsterModulesPatchApplicationHandlerMapping(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules,
    ProjectFolder jHipsterProjectFolderFactory
  ) {
    controllers = buildControllers(json, modulesResources, modules, jHipsterProjectFolderFactory);
  }

  private Map<String, JHipsterModuleApplicationController> buildControllers(
    ObjectMapper json,
    JHipsterModulesResources modulesResources,
    JHipsterModulesApplicationService modules,
    ProjectFolder jHipsterProjectFolderFactory
  ) {
    return modulesResources
      .stream()
      .collect(Collectors.toUnmodifiableMap(toApplyUrl(), toApplyerController(json, modules, jHipsterProjectFolderFactory)));
  }

  private Function<JHipsterModuleResource, String> toApplyUrl() {
    return moduleResource -> moduleResource.moduleUrl() + "/apply-patch";
  }

  private Function<JHipsterModuleResource, JHipsterModuleApplicationController> toApplyerController(
    ObjectMapper json,
    JHipsterModulesApplicationService modules,
    ProjectFolder jHipsterProjectFolderFactory
  ) {
    return module -> new JHipsterModuleApplicationController(json, module, modules, jHipsterProjectFolderFactory);
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
