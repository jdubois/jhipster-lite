package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import java.util.regex.Pattern;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.packagejson.VersionSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.ElementReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;

public class AngularOauth2ModuleFactory {

  private static final String APP_MODULE_IMPORTS =
    """
      import { LoginComponent } from './login/login.component';
      import { Oauth2AuthService } from './auth/oauth2-auth.service';
      import { HttpAuthInterceptor } from './auth/http-auth.interceptor';
      """;

  private static final String INIT_APP_METHOD =
    """
      const initializeApp = (oauth2AuthService: Oauth2AuthService) => {
        return () => {
          oauth2AuthService.initAuthentication();
        };
      };
      """;

  private static final ElementReplacer ANGULAR_CORE_IMPORT_NEEDLE = regex("\\} +from +['\"]@angular/core['\"];");
  private static final String APP_INITIALIZER_IMPORT = ", APP_INITIALIZER } from '@angular/core';";

  private static final ElementReplacer DECORATOR_NEEDLE = lineBeforeRegex("@[A-Z]{1}[\\w]+\\(\\{");

  private static final Pattern PROVIDERS_PATTERN = Pattern.compile("(providers: *\\[)");
  private static final ElementReplacer EXISTING_PROVIDERS_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> PROVIDERS_PATTERN.matcher(contentBeforeReplacement).find(),
    PROVIDERS_PATTERN
  );
  private static final ElementReplacer NEW_PROVIDERS_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> !PROVIDERS_PATTERN.matcher(contentBeforeReplacement).find(),
    "(declarations: *\\[[^]]*\\] *,)"
  );
  private static final String PROVIDERS =
    """
      { provide: APP_INITIALIZER, useFactory: initializeApp, multi: true, deps: [Oauth2AuthService] },
      { provide: HTTP_INTERCEPTORS, useClass: HttpAuthInterceptor, multi: true, },
      """;

  private static final ElementReplacer DECLARATIONS_NEEDLE = regex("(declarations: *\\[)");

  private static final ElementReplacer ENVIRONMENT_NEEDLE = lineAfterRegex("export const environment *= *\\{");
  private static final String KEYCLOAK_ENVIRONMENT =
    """
      keycloak: {
        url: 'http://localhost:9080',
        realm: 'jhipster',
        client_id: 'web_app'
      },
      """;

  private static final Pattern EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[\\s*)\\]"
  );
  private static final ElementReplacer EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    EMPTY_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final Pattern FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN = Pattern.compile(
    "(\"allowedCommonJsDependencies\": *\\[[^]]+)\\]"
  );
  private static final ElementReplacer FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE = new RegexReplacer(
    (contentBeforeReplacement, replacement) -> FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN.matcher(contentBeforeReplacement).find(),
    FILLED_ALLOWED_COMMON_DEPENDENCIES_PATTERN
  );

  private static final ElementReplacer MENU_NEEDLE = lineAfterRegex("<span.+id=\\\"menu-space-separator\\\".*></span>");

  private static final String TEST_IMPORTS =
    """
      import { By } from '@angular/platform-browser';
      import { LoginComponent } from './login/login.component';
      """;
  private static final ElementReplacer TEST_NEEDLE = lineAfterRegex("^\\s+it\\('should have appName',[^}]+\\}\\);");

  private static final String LOGIN_COMPONENT_TEST =
    """

      it('should display login component', () => {
        fixture.detectChanges();

        expect(fixture.debugElement.query(By.directive(LoginComponent))).toBeTruthy();
      });
      """;

  private static final JHipsterSource SOURCE = from("client/angular/security/oauth2/src/main/webapp/app");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("keycloak-js"), VersionSource.ANGULAR)
        .and()
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addFile("oauth2-auth.service.ts")
          .addFile("oauth2-auth.service.spec.ts")
          .addFile("http-auth.interceptor.ts")
          .addFile("http-auth.interceptor.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addFile("login.component.html")
          .addFile("login.component.ts")
          .addFile("login.component.spec.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in("src/main/webapp/app/app.module.ts")
          .add(fileStart(), APP_MODULE_IMPORTS)
          .add(ANGULAR_CORE_IMPORT_NEEDLE, APP_INITIALIZER_IMPORT)
          .add(DECORATOR_NEEDLE, INIT_APP_METHOD)
          .add(DECLARATIONS_NEEDLE, "$1LoginComponent, ")
          .add(EXISTING_PROVIDERS_NEEDLE, existingProviders(indentation))
          .add(NEW_PROVIDERS_NEEDLE, newProviders(indentation))
          .and()
        .in("src/main/webapp/environments/environment.ts")
          .add(ENVIRONMENT_NEEDLE, keycloakEnvironment(indentation))
          .and()
        .in("src/main/webapp/environments/environment.prod.ts")
          .add(ENVIRONMENT_NEEDLE, keycloakEnvironment(indentation))
          .and()
        .in("angular.json")
          .add(FILLED_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1, \"keycloak-js\"]")
          .add(EMPTY_ALLOWED_COMMON_DEPENDENCIES_NEEDLE, "$1\"keycloak-js\"]")
          .and()
        .in("src/main/webapp/app/app.component.html")
          .add(MENU_NEEDLE, indentation.spaces() + "<jhi-login></jhi-login>")
          .and()
        .in("src/main/webapp/app/app.component.spec.ts")
          .add(fileStart(), TEST_IMPORTS)
          .add(DECLARATIONS_NEEDLE, "$1LoginComponent, ")
          .add(TEST_NEEDLE, LOGIN_COMPONENT_TEST.indent(indentation.spacesCount() * 2))
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String existingProviders(Indentation indentation) {
    return new StringBuilder()
      .append("$1")
      .append(LINE_BREAK)
      .append(PROVIDERS.indent(indentation.spacesCount() * 2))
      .append(indentation.times(2))
      .toString();
  }

  private String newProviders(Indentation indentation) {
    return new StringBuilder()
      .append("$1")
      .append(LINE_BREAK)
      .append(indentation.spaces())
      .append("providers: [")
      .append(LINE_BREAK)
      .append(PROVIDERS.indent(indentation.spacesCount() * 2))
      .append("],")
      .toString();
  }

  private String keycloakEnvironment(Indentation indentation) {
    return KEYCLOAK_ENVIRONMENT.indent(indentation.spacesCount());
  }
}
