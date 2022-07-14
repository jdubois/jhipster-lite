package tech.jhipster.lite.generator.client.angular.common.application;

import static org.assertj.core.api.Assertions.*;
import static tech.jhipster.lite.TestUtils.*;
import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;
import static tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tech.jhipster.lite.IntegrationTest;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.module.infrastructure.secondary.TestJHipsterModules;

@IntegrationTest
class AngularCommonApplicationServiceIT {

  private static final String APP_MODULE_TS_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.module.ts");
  private static final String ENVIRONMENT_TS_FILE_PATH = getPath(MAIN_WEBAPP, "environments/environment.ts");
  private static final String APP_COMPONENT_HTML_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.component.html");
  private static final String APP_COMPONENT_SPEC_TS_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.component.spec.ts");
  private static final String ANGULAR_JSON_FILE_NAME = "angular.json";

  @Autowired
  private AngularCommonApplicationService angularCommonApplicationService;

  @Test
  void shouldAddImports() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String imports =
      """
        import {Oauth2AuthService} from '../../../auth/infrastructure/primary/oauth2-auth.service';
        import {HTTP_INTERCEPTORS} from '@angular/common/http';
        """;
    angularCommonApplicationService.addImports(project, APP_MODULE_TS_FILE_PATH, imports);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_MODULE_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
        import { NgModule } from '@angular/core';
        import { MatButtonToggleModule } from '@angular/material/button-toggle';
        import {Oauth2AuthService} from '../../../auth/infrastructure/primary/oauth2-auth.service';
        import {HTTP_INTERCEPTORS} from '@angular/common/http';
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddImportInExistingImport() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String imports = " APP_INITIALIZER";
    angularCommonApplicationService.addInExistingImport(project, APP_MODULE_TS_FILE_PATH, imports, "@angular/core");

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_MODULE_TS_FILE_PATH)));
    List<String> expectedContentLines = List.of("import { NgModule, APP_INITIALIZER} from '@angular/core';");
    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddConstants() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String constants = """
        const myFn = () => {
          return 'hello world';
        };
        """;
    angularCommonApplicationService.addConstants(project, APP_MODULE_TS_FILE_PATH, constants);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_MODULE_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
        import { NgModule } from '@angular/core';
        import { MatButtonToggleModule } from '@angular/material/button-toggle';

        const myFn = () => {
          return 'hello world';
        };

        @NgModule({
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddDeclarations() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String declarations = """
            MyComponent1, MyComponent2,
        """;
    angularCommonApplicationService.addDeclarations(project, APP_MODULE_TS_FILE_PATH, declarations);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_MODULE_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
        @NgModule({
          declarations: [AppComponent,
            MyComponent1, MyComponent2,
          ],
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddProviders() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String providers = """
            { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
        """;
    angularCommonApplicationService.addProviders(project, APP_MODULE_TS_FILE_PATH, providers);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_MODULE_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
          providers: [
            { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
          ],
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddEnvVariables() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String envVariables = """
          config1: {
            key1: "value1"
          }
        """;
    angularCommonApplicationService.addEnvVariables(project, ENVIRONMENT_TS_FILE_PATH, envVariables);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), ENVIRONMENT_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
        export const environment = {
          production: false,
          config1: {
            key1: "value1"
          }
        };
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddHtml() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String htmlToAdd = """
          <div>
            <h1>Hello World</h1>
          </div>
        """;
    angularCommonApplicationService.addHtml(project, APP_COMPONENT_HTML_FILE_PATH, htmlToAdd, "(<div id=\"footer\">)");

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_COMPONENT_HTML_FILE_PATH)));
    List<String> expectedContentLines =
      """
        <div id="footer">
          <div>
            <h1>Hello World</h1>
          </div>
        """.lines().toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddTest() throws IOException {
    // Given
    Project project = initAngularProject();

    // When
    String testToAdd =
      """
            it('should display login component',() => {
              // WHEN
              fixture.detectChanges();

              // THEN
              expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
            });
        """;
    angularCommonApplicationService.addTest(project, APP_COMPONENT_SPEC_TS_FILE_PATH, testToAdd, "should have appName");

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), APP_COMPONENT_SPEC_TS_FILE_PATH)));
    List<String> expectedContentLines =
      """
          describe('ngOnInit', () => {
            it('should have appName', () => {
              // WHEN
              fixture.detectChanges();

              // THEN
              expect(comp.appName).toEqual('foo');
            });

            it('should display login component',() => {
              // WHEN
              fixture.detectChanges();

              // THEN
              expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
            });
          });
        """.lines()
        .toList();

    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  @Test
  void shouldAddAllowedCommonJsDependenciesAngularJson() throws IOException {
    // Given
    Project project = initAngularProject();
    String lib = "  \"libA\"";
    // When
    angularCommonApplicationService.addAllowedCommonJsDependenciesAngularJson(project, lib);

    // Then
    List<String> allLinesFile = Files.readAllLines(Path.of(getPath(project.getFolder(), ANGULAR_JSON_FILE_NAME)));
    List<String> expectedContentLines =
      """
                    "allowedCommonJsDependencies": [
          "libA"
                    ]
        """.lines().toList();
    assertThat(allLinesFile).containsAll(expectedContentLines);
  }

  private Project initAngularProject() {
    Project project = tmpProject();
    project.addConfig(BASE_NAME, "foo");
    TestJHipsterModules.applyInit(project);
    applyAngular(project);
    return project;
  }
}
