package tech.jhipster.lite.generator.client.angular.security.oauth2.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;

import java.util.List;
import java.util.Map;

public class AngularOauth2 {

  private AngularOauth2() {
    // Cannot be instantiated
  }

  protected static final String APP_MODULE_TS_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.module.ts");
  protected static final String ENVIRONMENT_TS_FILE_PATH = getPath(MAIN_WEBAPP, "environments/environment.ts");
  protected static final String ENVIRONMENT_PROD_TS_FILE_PATH = getPath(MAIN_WEBAPP, "environments/environment.prod.ts");
  protected static final String APP_COMPONENT_HTML_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.component.html");
  protected static final String APP_COMPONENT_SPEC_TS_FILE_PATH = getPath(MAIN_WEBAPP, "app/app.component.spec.ts");

  protected static List<String> getDependencies() {
    return List.of("keycloak-js");
  }

  protected static Map<String, String> getFilesToAdd() {
    String authFolderPath = "app/auth";
    String loginFolderPath = "app/login";
    return Map.ofEntries(
      Map.entry("oauth2-auth.service.ts", authFolderPath),
      Map.entry("oauth2-auth.service.spec.ts", authFolderPath),
      Map.entry("http-auth.interceptor.ts", authFolderPath),
      Map.entry("http-auth.interceptor.spec.ts", authFolderPath),
      Map.entry("login.component.html", loginFolderPath),
      Map.entry("login.component.ts", loginFolderPath),
      Map.entry("login.component.spec.ts", loginFolderPath)
    );
  }
}
