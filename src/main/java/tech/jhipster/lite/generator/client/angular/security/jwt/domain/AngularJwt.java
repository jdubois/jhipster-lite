package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import java.util.List;
import java.util.Map;

public class AngularJwt {

  private AngularJwt() {}

  public static List<String> jwtDependencies() {
    return List.of("ngx-webstorage");
  }

  public static Map<String, String> angularJwtFiles() {
    String primaryAppAuth = "app/auth";
    String primaryAppLogin = "app/login";
    return Map.ofEntries(
      Map.entry("account.model.ts", primaryAppAuth),
      Map.entry("account.model.spec.ts", primaryAppAuth),
      Map.entry("account.service.ts", primaryAppAuth),
      Map.entry("account.service.spec.ts", primaryAppAuth),
      Map.entry("auth.interceptor.ts", primaryAppAuth),
      Map.entry("auth.interceptor.spec.ts", primaryAppAuth),
      Map.entry("auth-jwt.service.ts", primaryAppAuth),
      Map.entry("auth-jwt.service.spec.ts", primaryAppAuth),
      Map.entry("login.service.ts", primaryAppLogin),
      Map.entry("login.service.spec.ts", primaryAppLogin),
      Map.entry("login.model.ts", primaryAppLogin),
      Map.entry("login.component.css", primaryAppLogin),
      Map.entry("login.component.html", primaryAppLogin),
      Map.entry("login.component.spec.ts", primaryAppLogin),
      Map.entry("login.component.ts", primaryAppLogin),
      Map.entry("login.module.ts", primaryAppLogin),
      Map.entry("login.route.ts", primaryAppLogin)
    );
  }
}
