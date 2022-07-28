package tech.jhipster.lite.generator.client.angular.security.oauth2.application;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.common.domain.WordUtils.DQ;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.Constants.PACKAGE_JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;

public class AngularOauth2Assert {

  private static final List<String> ADDED_FILES_PATHS = List.of(
    getPath(MAIN_WEBAPP, "app/auth/oauth2-auth.service.ts"),
    getPath(MAIN_WEBAPP, "app/auth/oauth2-auth.service.spec.ts"),
    getPath(MAIN_WEBAPP, "app/auth/http-auth.interceptor.ts"),
    getPath(MAIN_WEBAPP, "app/auth/http-auth.interceptor.spec.ts"),
    getPath(MAIN_WEBAPP, "app/login/login.component.html"),
    getPath(MAIN_WEBAPP, "app/login/login.component.ts"),
    getPath(MAIN_WEBAPP, "app/login/login.component.spec.ts")
  );

  private static final List<String> ADDED_DEPENDENCIES = List.of("keycloak-js");

  private AngularOauth2Assert() {}

  public static void assertDependencies(Project project) {
    ADDED_DEPENDENCIES.forEach(dependency -> assertFileContent(project, PACKAGE_JSON, DQ + dependency + DQ));
  }

  public static void assertAddedFiles(Project project) {
    ADDED_FILES_PATHS.forEach(filePath -> assertFileExist(project, filePath));
  }

  public static void assertUpdatedFiles(Project project) throws IOException {
    // App.module.ts
    List<String> appModuleTsLines = Files.readAllLines(Path.of(getPath(project.getFolder(), "src/main/webapp/app/app.module.ts")));
    List<String> expectedContentLines =
      """
        import { NgModule, APP_INITIALIZER} from '@angular/core';
        import { MatButtonToggleModule } from '@angular/material/button-toggle';
        import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
        import { LoginComponent } from './login/login.component';
        import { Oauth2AuthService } from './auth/oauth2-auth.service';
        import { HttpAuthInterceptor } from './auth/http-auth.interceptor';

        const initializeApp = (oauth2AuthService: Oauth2AuthService) => {
          return () => {
            oauth2AuthService.initAuthentication();
          };
        };

        @NgModule({
          declarations: [AppComponent,
            LoginComponent,
          ],
          providers: [
            { provide: APP_INITIALIZER, useFactory: initializeApp, multi: true, deps: [Oauth2AuthService] },
            { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
          ],
        })

        """.lines()
        .toList();
    assertThat(appModuleTsLines).containsAll(expectedContentLines);

    // Environnement.ts
    assertFileContent(
      project,
      "src/main/webapp/environments/environment.ts",
      """
    export const environment = {
      production: false,
      keycloak: {
        url: 'http://localhost:9080/auth',
        realm: 'jhipster',
        client_id: 'web_app'
      }
    };
    """.lines()
        .toList()
    );

    // Environnement-prod.ts
    assertFileContent(
      project,
      "src/main/webapp/environments/environment.prod.ts",
      """
      export const environment = {
        production: true,
        keycloak: {
          url: 'http://localhost:9080/auth',
          realm: 'jhipster',
          client_id: 'web_app'
        }
      };
      """.lines()
        .toList()
    );

    // Angular.json
    List<String> angularJsonTsLines = Files.readAllLines(Path.of(getPath(project.getFolder(), "angular.json")));
    expectedContentLines =
      """
                  "scripts": [],
                  "allowedCommonJsDependencies": [
                    "keycloak-js"
                  ]
                },
      """.lines()
        .toList();

    assertThat(angularJsonTsLines).containsAll(expectedContentLines);

    // App.component.html
    assertFileContent(project, "src/main/webapp/app/app.component.html", "  <jhi-login></jhi-login>");

    // App.component.spec.ts
    List<String> appComponentSpecTsLines = Files.readAllLines(
      Path.of(getPath(project.getFolder(), "src/main/webapp/app/app.component.spec.ts"))
    );
    expectedContentLines =
      """
        import { AppComponent } from './app.component';
        import { By } from '@angular/platform-browser';
        import { LoginComponent } from './login/login.component';

          beforeEach(
            waitForAsync(() => {
              TestBed.configureTestingModule({
                imports: [RouterTestingModule.withRoutes([]), MatToolbarModule, MatIconModule, MatMenuModule],
                declarations: [AppComponent,
                  LoginComponent,
                ],
              }).compileComponents();
            })
          );

            it('should display login component',() => {
              // WHEN
              fixture.detectChanges();

              // THEN
              expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
            });
        """.lines()
        .toList();
    assertThat(appComponentSpecTsLines).containsAll(expectedContentLines);
  }
}
