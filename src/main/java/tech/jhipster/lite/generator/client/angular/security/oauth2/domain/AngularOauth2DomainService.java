package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.APP_COMPONENT_HTML_FILE_PATH;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.APP_COMPONENT_SPEC_TS_FILE_PATH;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.APP_MODULE_TS_FILE_PATH;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.ENVIRONMENT_PROD_TS_FILE_PATH;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.ENVIRONMENT_TS_FILE_PATH;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.BASE_NAME;

import java.util.List;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class AngularOauth2DomainService implements AngularOauth2Service {

  public static final String SOURCE = "client/angular/security/oauth2";
  public static final String SOURCE_WEBAPP = "client/angular/security/oauth2/" + MAIN_WEBAPP;

  private final ProjectRepository projectRepository;
  private final NpmService npmService;
  private final AngularCommonService angularCommonService;

  public AngularOauth2DomainService(ProjectRepository projectRepository, NpmService npmService, AngularCommonService angularCommonService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
    this.angularCommonService = angularCommonService;
  }

  @Override
  public void addOauth2(Project project) {
    addDependencies(project);
    addFiles(project);
    updateExistingFiles(project);
  }

  private void addDependencies(Project project) {
    AngularOauth2
      .getDependencies()
      .forEach(dependency ->
        npmService
          .getVersion("angular", dependency)
          .ifPresentOrElse(
            version -> npmService.addDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("Dependency not found: " + dependency);
            }
          )
      );
  }

  private void addFiles(Project project) {
    project.addDefaultConfig(BASE_NAME);
    List<ProjectFile> projectFiles = AngularOauth2
      .getFilesToAdd()
      .entrySet()
      .stream()
      .map(e ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE_WEBAPP, e.getValue()), e.getKey())
          .withDestinationFolder(getPath(MAIN_WEBAPP, e.getValue()))
      )
      .toList();
    projectRepository.add(projectFiles);
  }

  private void updateExistingFiles(Project project) {
    updateAppModuleTsFile(project);
    addEnvVariables(project);
    addKeycloakJsAllowedCommonJsDependency(project);
    addLoginComponentInAppHtmlFile(project);
    addTestInAppSpecTsFile(project);
  }

  private void updateAppModuleTsFile(Project project) {
    angularCommonService.addImports(
      project,
      APP_MODULE_TS_FILE_PATH,
      """
        import { HTTP_INTERCEPTORS } from '@angular/common/http';
        import { LoginComponent } from './login/login.component';
        import { Oauth2AuthService } from './auth/oauth2-auth.service';
        import { HttpAuthInterceptor } from './auth/http-auth.interceptor';
        """
    );

    angularCommonService.addInExistingImport(project, APP_MODULE_TS_FILE_PATH, " APP_INITIALIZER", "@angular/core");

    angularCommonService.addConstants(
      project,
      APP_MODULE_TS_FILE_PATH,
      """
        const initializeApp = (oauth2AuthService: Oauth2AuthService) => {
          return () => {
            oauth2AuthService.initAuthentication();
          };
        };
        """
    );

    angularCommonService.addDeclarations(project, APP_MODULE_TS_FILE_PATH, """
            LoginComponent,
        """);

    angularCommonService.addProviders(
      project,
      APP_MODULE_TS_FILE_PATH,
      """
            { provide: APP_INITIALIZER, useFactory: initializeApp, multi: true, deps: [Oauth2AuthService] },
            { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
        """
    );
  }

  private void addEnvVariables(Project project) {
    String envVariables =
      """
        keycloak: {
          url: 'http://localhost:9080/auth',
          realm: 'jhipster',
          client_id: 'web_app'
        }
      """;
    angularCommonService.addEnvVariables(project, ENVIRONMENT_TS_FILE_PATH, envVariables);
    angularCommonService.addEnvVariables(project, ENVIRONMENT_PROD_TS_FILE_PATH, envVariables);
  }

  private void addKeycloakJsAllowedCommonJsDependency(Project project) {
    angularCommonService.addAllowedCommonJsDependenciesAngularJson(project, "              \"keycloak-js\"");
  }

  private void addLoginComponentInAppHtmlFile(Project project) {
    angularCommonService.addHtml(
      project,
      APP_COMPONENT_HTML_FILE_PATH,
      """
          <jh-lite-login></jh-lite-login>
        """,
      "(<span.+id=\\\"menu-space-separator\\\".*></span>)"
    );
  }

  private void addTestInAppSpecTsFile(Project project) {
    angularCommonService.addImports(
      project,
      APP_COMPONENT_SPEC_TS_FILE_PATH,
      """
        import { By } from '@angular/platform-browser';
        import { LoginComponent } from './login/login.component';
        """
    );
    angularCommonService.addDeclarations(project, APP_COMPONENT_SPEC_TS_FILE_PATH, """
                  LoginComponent,
        """);
    angularCommonService.addTest(
      project,
      APP_COMPONENT_SPEC_TS_FILE_PATH,
      """
          it('should display login component',() => {
            // WHEN
            fixture.detectChanges();

            // THEN
            expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
          });
      """,
      "should have appName"
    );
  }
}
