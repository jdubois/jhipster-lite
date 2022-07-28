package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.groups.Tuple.tuple;
import static org.mockito.Mockito.*;
import static tech.jhipster.lite.generator.client.angular.security.oauth2.domain.AngularOauth2.*;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

@UnitTest
@ExtendWith(MockitoExtension.class)
class AngularOauth2DomainServiceTest {

  @Mock
  ProjectRepository projectRepository;

  @Mock
  NpmService npmService;

  @Mock
  AngularCommonService angularCommonService;

  @InjectMocks
  AngularOauth2DomainService angularOauth2DomainService;

  @Captor
  ArgumentCaptor<List<ProjectFile>> projectFilesArgCaptor;

  @Test
  void shouldAddOauth2() {
    // Given
    Project project = Project.builder().folder("/project/path").build();
    when(npmService.getVersion("angular", "keycloak-js")).thenReturn(Optional.of("0.0.0"));

    // When
    angularOauth2DomainService.addOauth2(project);

    // Then
    verify(npmService).addDependency(project, "keycloak-js", "0.0.0");
    verifyAddedFiles(project);
    verifyUpdatedFiles(project);
  }

  private void verifyAddedFiles(Project project) {
    verify(projectRepository).add(projectFilesArgCaptor.capture());
    String authSourcePath = "client/angular/security/oauth2/src/main/webapp/app/auth";
    String authDestinationPath = "src/main/webapp/app/auth";
    String loginSourcePath = "client/angular/security/oauth2/src/main/webapp/app/login";
    String loginDestinationPath = "src/main/webapp/app/login";
    assertThat(projectFilesArgCaptor.getValue())
      .extracting(ProjectFile::project, p -> p.source().folder(), p -> p.source().file(), p -> p.destination().folder())
      .containsExactlyInAnyOrder(
        tuple(project, authSourcePath, "oauth2-auth.service.ts", authDestinationPath),
        tuple(project, authSourcePath, "oauth2-auth.service.spec.ts", authDestinationPath),
        tuple(project, authSourcePath, "http-auth.interceptor.ts", authDestinationPath),
        tuple(project, authSourcePath, "http-auth.interceptor.spec.ts", authDestinationPath),
        tuple(project, loginSourcePath, "login.component.html", loginDestinationPath),
        tuple(project, loginSourcePath, "login.component.ts", loginDestinationPath),
        tuple(project, loginSourcePath, "login.component.spec.ts", loginDestinationPath)
      );
  }

  @Test
  void shouldThrowExceptionWhenDependencyVersionNotFound() {
    // Given
    Project project = Project.builder().folder("/project/path").build();
    when(npmService.getVersion("angular", "keycloak-js")).thenReturn(Optional.empty());

    // When + Then
    assertThatThrownBy(() -> angularOauth2DomainService.addOauth2(project))
      .isInstanceOf(GeneratorException.class)
      .hasMessageContaining("Dependency not found: keycloak-js");
  }

  private void verifyUpdatedFiles(Project project) {
    verify(angularCommonService, times(2)).addImports(any(Project.class), anyString(), anyString());
    verify(angularCommonService, times(1)).addInExistingImport(any(Project.class), anyString(), anyString(), anyString());
    verify(angularCommonService, times(1)).addConstants(any(Project.class), anyString(), anyString());
    verify(angularCommonService, times(2)).addDeclarations(any(Project.class), anyString(), anyString());
    verify(angularCommonService, times(1)).addProviders(any(Project.class), anyString(), anyString());
    verify(angularCommonService, times(2)).addEnvVariables(any(Project.class), anyString(), anyString());
    verify(angularCommonService, times(1)).addAllowedCommonJsDependenciesAngularJson(any(Project.class), anyString());
    verify(angularCommonService, times(1)).addHtml(any(Project.class), anyString(), anyString(), anyString());
    verify(angularCommonService, times(1)).addTest(any(Project.class), anyString(), anyString(), anyString());

    verify(angularCommonService)
      .addImports(
        project,
        APP_MODULE_TS_FILE_PATH,
        """
        import { LoginComponent } from './login/login.component';
        import { Oauth2AuthService } from './auth/oauth2-auth.service';
        import { HttpAuthInterceptor } from './auth/http-auth.interceptor';
        """
      );

    verify(angularCommonService).addInExistingImport(project, APP_MODULE_TS_FILE_PATH, " APP_INITIALIZER", "@angular/core");

    verify(angularCommonService)
      .addConstants(
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

    verify(angularCommonService).addDeclarations(project, APP_MODULE_TS_FILE_PATH, """
            LoginComponent,
        """);

    verify(angularCommonService)
      .addProviders(
        project,
        APP_MODULE_TS_FILE_PATH,
        """
            { provide: APP_INITIALIZER, useFactory: initializeApp, multi: true, deps: [Oauth2AuthService] },
            { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
        """
      );

    String envVariables =
      """
        keycloak: {
          url: 'http://localhost:9080/auth',
          realm: 'jhipster',
          client_id: 'web_app'
        }
      """;
    verify(angularCommonService).addEnvVariables(project, ENVIRONMENT_TS_FILE_PATH, envVariables);
    verify(angularCommonService).addEnvVariables(project, ENVIRONMENT_PROD_TS_FILE_PATH, envVariables);

    verify(angularCommonService).addAllowedCommonJsDependenciesAngularJson(project, "              \"keycloak-js\"");

    verify(angularCommonService)
      .addHtml(
        project,
        APP_COMPONENT_HTML_FILE_PATH,
        """
          <jhi-login></jhi-login>
        """,
        "(<span.+id=\\\"menu-space-separator\\\".*></span>)"
      );

    verify(angularCommonService)
      .addImports(
        project,
        APP_COMPONENT_SPEC_TS_FILE_PATH,
        """
        import { By } from '@angular/platform-browser';
        import { LoginComponent } from './login/login.component';
        """
      );

    verify(angularCommonService).addDeclarations(project, APP_COMPONENT_SPEC_TS_FILE_PATH, """
                  LoginComponent,
        """);
    verify(angularCommonService)
      .addTest(
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
