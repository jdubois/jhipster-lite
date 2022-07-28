package tech.jhipster.lite.project.infrastructure.primary;

import org.springframework.context.ApplicationListener;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.stereotype.Component;
import tech.jhipster.lite.module.domain.JHipsterModuleApplied;
import tech.jhipster.lite.project.application.ProjectsApplicationService;
import tech.jhipster.lite.project.domain.ProjectPath;
import tech.jhipster.lite.project.domain.history.ProjectAction;
import tech.jhipster.lite.project.domain.history.ProjectActionToAppend;

@Component("ProjectSpringJHipsterModuleAppliedListener")
class SpringJHipsterModuleAppliedListener implements ApplicationListener<PayloadApplicationEvent<JHipsterModuleApplied>> {

  private final ProjectsApplicationService projects;

  public SpringJHipsterModuleAppliedListener(ProjectsApplicationService projects) {
    this.projects = projects;
  }

  @Override
  public void onApplicationEvent(PayloadApplicationEvent<JHipsterModuleApplied> event) {
    projects.append(projectActionToAdd(event.getPayload()));
  }

  private static ProjectActionToAppend projectActionToAdd(JHipsterModuleApplied moduleApplied) {
    ProjectPath path = new ProjectPath(moduleApplied.properties().projectFolder().get());

    ProjectAction action = ProjectAction
      .builder()
      .module(moduleApplied.slug().get())
      .date(moduleApplied.time())
      .properties(moduleApplied.properties().get());

    return new ProjectActionToAppend(path, action);
  }
}
