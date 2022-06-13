package tech.jhipster.lite.generator.client.vue.core.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Vue {

  public static final String ROUTER_IMPORT = "import router from \'./router/router\';";
  public static final String ROUTER_PROVIDER = "app.use(router);";
  public static final Collection<String> PINIA_IMPORTS = List.of(
    "import {createPinia} from 'pinia';",
    "import piniaPersist from 'pinia-plugin-persist'"
  );
  public static final Collection<String> PINIA_PROVIDERS = List.of(
    "const pinia = createPinia();",
    "pinia.use(piniaPersist);",
    "app.use(pinia);"
  );

  private Vue() {}

  public static List<String> dependencies() {
    return List.of("vue");
  }

  public static List<String> piniaDependencies() {
    return List.of("pinia", "pinia-plugin-persist");
  }

  public static List<String> piniaDevDependencies() {
    return List.of("@pinia/testing");
  }

  public static List<String> routerDependencies() {
    return List.of("vue-router");
  }

  public static List<String> devDependencies() {
    return List.of(
      "@rushstack/eslint-patch",
      "@types/jest",
      "@typescript-eslint/parser",
      "@vitejs/plugin-vue",
      "@vue/eslint-config-typescript",
      "@vue/test-utils",
      "eslint",
      "eslint-config-prettier",
      "eslint-plugin-vue",
      "jest",
      "jest-sonar-reporter",
      "jest-transform-stub",
      "ts-jest",
      "typescript",
      "vite",
      "vue-jest",
      "vue-tsc",
      "@types/sinon",
      "sinon"
    );
  }

  public static List<String> axiosDependency() {
    return List.of("axios");
  }

  public static Map<String, String> scripts() {
    // @formatter:off
    return Map
      .of(
        "build", "vue-tsc --noEmit && vite build --emptyOutDir",
        "dev", "vite",
        "jest", "jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache",
        "preview", "vite preview",
        "start", "vite",
        "test", "npm run jest --",
        "test:watch", "npm run jest -- --watch"
      );
    // @formatter:on
  }
}
