package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;
import static tech.jhipster.lite.generator.project.domain.Constants.TEST_JAVASCRIPT;

import java.util.List;
import java.util.Map;

public class ReactJwt {

  public static final String SOURCE_APP_SERVICES = MAIN_WEBAPP + "/app/common/services";

  public static final String SOURCE_LOGIN_FORM = MAIN_WEBAPP + "/app/login/primary/loginForm";

  public static final String SOURCE_LOGIN_MODAL = MAIN_WEBAPP + "/app/login/primary/loginModal";

  public static final String SOURCE_LOGIN_SERVICES = MAIN_WEBAPP + "/app/login/services";

  public static final String PATH_TEST_LOGIN_FORM = TEST_JAVASCRIPT + "/login/primary/loginForm";

  public static final String PATH_TEST_LOGIN_MODAL = TEST_JAVASCRIPT + "/login/primary/loginModal";

  public static final String PATH_TEST_LOGIN_SERVICES = TEST_JAVASCRIPT + "/login/services";

  public static final String PATH_TEST_APP_SERVICES = TEST_JAVASCRIPT + "/common/services";

  private ReactJwt() {}

  public static List<String> dependencies() {
    return List.of("react-hook-form", "axios", "@nextui-org/react");
  }

  public static List<String> devDependencies() {
    return List.of("sass");
  }

  public static Map<String, List<String>> reactJwtFiles() {
    return Map.ofEntries(
      Map.entry("storage.ts", List.of(SOURCE_APP_SERVICES)),
      Map.entry("index.tsx", List.of(SOURCE_LOGIN_FORM, SOURCE_LOGIN_MODAL)),
      Map.entry("interface.d.ts", List.of(SOURCE_LOGIN_MODAL)),
      Map.entry("LoginModal.scss", List.of(SOURCE_LOGIN_MODAL)),
      Map.entry("login.ts", List.of(SOURCE_LOGIN_SERVICES)),
      Map.entry("login.test.ts", List.of(PATH_TEST_LOGIN_SERVICES)),
      Map.entry("index.test.tsx", List.of(PATH_TEST_LOGIN_FORM, PATH_TEST_LOGIN_MODAL)),
      Map.entry("storage.test.ts", List.of(PATH_TEST_APP_SERVICES))
    );
  }
}
