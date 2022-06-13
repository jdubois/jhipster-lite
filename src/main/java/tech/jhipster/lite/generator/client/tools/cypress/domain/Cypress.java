package tech.jhipster.lite.generator.client.tools.cypress.domain;

import java.util.List;
import java.util.Map;

public class Cypress {

  private Cypress() {}

  public static final String JAVASCRIPT_INTEGRATION = "src/test/javascript/integration";

  public static List<String> devDependencies() {
    return List.of("cypress", "eslint-plugin-cypress");
  }

  public static Map<String, String> cypressScripts() {
    return Map.of(
      "e2e",
      "npm run test:component",
      "e2e:headless",
      "npm run test:component:headless",
      "test:component",
      "cypress open --config-file src/test/javascript/integration/cypress-config.ts",
      "test:component:headless",
      "cypress run --headless --config-file src/test/javascript/integration/cypress-config.ts"
    );
  }

  public static Map<String, String> cypressFiles() {
    return Map.ofEntries(Map.entry(".eslintrc.js", JAVASCRIPT_INTEGRATION), Map.entry("tsconfig.json", JAVASCRIPT_INTEGRATION));
  }

  public static Map<String, String> cypressTestFiles() {
    String pathIntegrationTestPrimaryApp = "src/test/javascript/integration/common/primary/app";

    return Map.ofEntries(Map.entry("Home.spec.ts", pathIntegrationTestPrimaryApp));
  }

  public static List<String> tsconfigPatternsToExclude() {
    return List.of("src/test/javascript/integration/**/*.ts");
  }
}
