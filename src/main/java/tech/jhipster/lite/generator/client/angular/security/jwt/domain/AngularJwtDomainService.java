package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.client.angular.core.domain.Angular.*;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import java.util.List;
import java.util.stream.Collectors;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class AngularJwtDomainService implements AngularJwtService {

  public static final String SOURCE = "client/angular/security/jwt";
  public static final String SOURCE_WEBAPP = "client/angular/security/jwt/src/main/webapp";
  private static final String APP = "src/main/webapp/app";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;
  private final AngularCommonService angularCommonService;

  public AngularJwtDomainService(ProjectRepository projectRepository, NpmService npmService, AngularCommonService angularCommonService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
    this.angularCommonService = angularCommonService;
  }

  @Override
  public void addJwtAngular(Project project) {
    addAppJwtFiles(project);
  }

  public void addAppJwtFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    addJwtDependencies(project);
    addAngularJwtFiles(project);
    updateAngularFilesForJwt(project);
  }

  public void addJwtDependencies(Project project) {
    AngularJwt.jwtDependencies().forEach(dependency -> addDependency(project, dependency));
  }

  public void updateAngularFilesForJwt(Project project) {
    String oldHtml = "// jhipster-needle-angular-route";
    String newHtml =
      """
        // jhipster-needle-angular-route
        {
          path: '',
          loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
        },""";
    projectRepository.replaceText(project, APP, APP_ROUTING_MODULE, oldHtml, newHtml);

    oldHtml = "import \\{ NgModule \\} from '@angular/core';";
    newHtml =
      """
        import { NgModule } from '@angular/core';
        import { ReactiveFormsModule } from '@angular/forms';
        import { NgxWebstorageModule } from 'ngx-webstorage';""";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "import \\{ AppComponent \\} from './app.component';";
    newHtml =
      """
        import { AppComponent } from './app.component';
        import { AuthInterceptor } from './auth/auth.interceptor';
        """;
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "imports: \\[" + angularCommonService.getAngularModules().stream().collect(Collectors.joining(", ")) + "\\],";
    newHtml =
      "imports: \\[" +
      angularCommonService.getAngularModules().stream().collect(Collectors.joining(", ")) +
      ", ReactiveFormsModule, NgxWebstorageModule.forRoot()\\],";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);

    oldHtml = "bootstrap: \\[AppComponent\\],";
    newHtml =
      """
        bootstrap: [AppComponent],
          providers: [
            {
              provide: HTTP_INTERCEPTORS,
              useClass: AuthInterceptor,
              multi: true,
            },
          ],""";
    projectRepository.replaceText(project, APP, APP_MODULE, oldHtml, newHtml);
  }

  public void addAngularJwtFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);

    List<ProjectFile> files = AngularJwt
      .angularJwtFiles()
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

  private void addDependency(Project project, String dependency) {
    npmService
      .getVersion("angular", dependency)
      .ifPresentOrElse(
        version -> npmService.addDependency(project, dependency, version),
        () -> {
          throw new GeneratorException("Dependency not found: " + dependency);
        }
      );
  }
}
