package tech.jhipster.lite.generator.client.angular.admin.health.domain;

import java.util.Map;

public class AngularHealth {

  private AngularHealth() {}

  public static Map<String, String> angularHealthFiles() {
    String primaryAppAdmin = "app/admin";
    String primaryAppConfig = "app/config";
    String primaryAppHealth = "app/admin/health";
    String primaryAppHealthModal = "app/admin/health/modal";
    return Map.ofEntries(
      Map.entry("admin-routing.module.ts", primaryAppAdmin),
      Map.entry("admin-routing.module.spec.ts", primaryAppAdmin),
      Map.entry("application-config.service.spec.ts", primaryAppConfig),
      Map.entry("application-config.service.ts", primaryAppConfig),
      Map.entry("health.component.css", primaryAppHealth),
      Map.entry("health.component.html", primaryAppHealth),
      Map.entry("health.component.ts", primaryAppHealth),
      Map.entry("health.component.spec.ts", primaryAppHealth),
      Map.entry("health.model.ts", primaryAppHealth),
      Map.entry("health.module.ts", primaryAppHealth),
      Map.entry("health.route.ts", primaryAppHealth),
      Map.entry("health.service.spec.ts", primaryAppHealth),
      Map.entry("health.service.ts", primaryAppHealth),
      Map.entry("health-modal.component.css", primaryAppHealthModal),
      Map.entry("health-modal.component.html", primaryAppHealthModal),
      Map.entry("health-modal.component.ts", primaryAppHealthModal),
      Map.entry("health-modal.component.spec.ts", primaryAppHealthModal)
    );
  }
}
