package tech.jhipster.lite.generator.client.angular.admin.health.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.*;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class AngularHealthDomainService implements AngularHealthService {

  public static final String SOURCE = "client/angular/admin";
  public static final String SOURCE_WEBAPP = "client/angular/admin/src/main/webapp";
  private static final String APP = "src/main/webapp/app";

  private final ProjectRepository projectRepository;

  public AngularHealthDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addHealthAngular(Project project) {
    addAppHealthFiles(project);
  }

  private void addAppHealthFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    addHealthFiles(project);
    updateAngularFilesForHealth(project);
  }

  private void addHealthFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    List<ProjectFile> files = AngularHealth
      .angularHealthFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE_WEBAPP, entry.getValue()), entry.getKey())
          .withDestinationFolder(getPath(MAIN_WEBAPP, entry.getValue()))
      )
      .toList();
    projectRepository.template(files);
  }

  private void updateAngularFilesForHealth(Project project) {
    String oldHtml = "// jhipster-needle-angular-route";
    String newHtml =
      """
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
      },
      // jhipster-needle-angular-route""";
    projectRepository.replaceText(project, APP, APP_ROUTING_MODULE, oldHtml, newHtml);

    oldHtml = "<!-- jhipster-needle-angular-menu -->";
    newHtml = """
      <a routerLink="admin/health" mat-menu-item><span>Health</span></a>
      <!-- jhipster-needle-angular-menu -->""";
    projectRepository.replaceText(project, APP, APP_COMPONENT_HTML, oldHtml, newHtml);

    oldHtml = "// jhipster-needle-angular-menu";
    newHtml =
      """
      it('should navigate on admin endpoint', () => {
          router.navigateByUrl('/admin');
      });
      // jhipster-needle-angular-menu""";
    projectRepository.replaceText(project, APP, APP_ROUTING_MODULE_SPEC, oldHtml, newHtml);

    oldHtml = "// jhipster-needle-angular-menu";
    newHtml =
      """
        it('should navigate on health endpoint', () => {
            router.navigateByUrl('/health');
        });
        // jhipster-needle-angular-menu""";
    projectRepository.replaceText(project, APP, ADMIN_ROUTING_MODULE_SPEC, oldHtml, newHtml);
  }
}
