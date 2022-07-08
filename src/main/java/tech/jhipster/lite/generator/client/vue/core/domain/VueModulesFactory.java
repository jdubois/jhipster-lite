package tech.jhipster.lite.generator.client.vue.core.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterDestination;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class VueModulesFactory {

  private static final JHipsterSource SOURCE = from("client/vue");
  private static final JHipsterSource IMAGE_SOURCE = SOURCE.append("webapp/content/images");
  private static final JHipsterSource COMMON_PRIMARY_SOURCE = SOURCE.append("webapp/app/common/primary");
  private static final JHipsterSource COMMON_PRIMARY_TEST_SOURCE = SOURCE.append("test/spec/common/primary");

  private static final JHipsterDestination MAIN_DESTINATION = to("src/main/webapp/app");
  private static final JHipsterDestination MAIN_PRIMARY_DESTINATION = MAIN_DESTINATION.append("common/primary");
  private static final JHipsterDestination COMMON_PRIMARY_TEST_DESTINATION = to("src/test/javascript/spec/common/primary");

  private static final String IMPORT_NEEDLE = "// jhipster-needle-main-ts-import";
  private static final String PROVIDER_NEEDLE = "// jhipster-needle-main-ts-provider";
  private static final String CACHE_NEEDLE = "  \"cacheDirectories\":";
  private static final String BREAK = "\n";

  public JHipsterModule buildVueModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("vue"), VUE)
        .addDependency(packageName("axios"), VUE)
        .addDependency(packageName("vue-router"), VUE)
        .addDevDependency(packageName("@rushstack/eslint-patch"), VUE)
        .addDevDependency(packageName("@types/jest"), VUE)
        .addDevDependency(packageName("@typescript-eslint/parser"), VUE)
        .addDevDependency(packageName("@vitejs/plugin-vue"), VUE)
        .addDevDependency(packageName("@vue/eslint-config-typescript"), VUE)
        .addDevDependency(packageName("@vue/test-utils"), VUE)
        .addDevDependency(packageName("eslint"), VUE)
        .addDevDependency(packageName("eslint-config-prettier"), VUE)
        .addDevDependency(packageName("eslint-plugin-vue"), VUE)
        .addDevDependency(packageName("jest"), VUE)
        .addDevDependency(packageName("jest-sonar-reporter"), VUE)
        .addDevDependency(packageName("jest-transform-stub"), VUE)
        .addDevDependency(packageName("ts-jest"), VUE)
        .addDevDependency(packageName("typescript"), VUE)
        .addDevDependency(packageName("vite"), VUE)
        .addDevDependency(packageName("vue-jest"), VUE)
        .addDevDependency(packageName("vue-tsc"), VUE)
        .addDevDependency(packageName("@types/sinon"), VUE)
        .addDevDependency(packageName("sinon"), VUE)
        .addScript(scriptKey("build"), scriptCommand("vue-tsc --noEmit && vite build --emptyOutDir"))
        .addScript(scriptKey("dev"), scriptCommand("vite"))
        .addScript(scriptKey("jest"), scriptCommand("jest src/test/javascript/spec --logHeapUsage --maxWorkers=2 --no-cache"))
        .addScript(scriptKey("preview"), scriptCommand("vite preview"))
        .addScript(scriptKey("start"), scriptCommand("vite"))
        .addScript(scriptKey("test"), scriptCommand("npm run jest --"))
        .addScript(scriptKey("test:watch"), scriptCommand("npm run jest -- --watch"))
        .and()
      .mandatoryReplacements()
        .in("package.json")
          .add(justLineBefore(text(CACHE_NEEDLE)), jestSonar(properties.indentation()))
          .and()
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.js"), to(".eslintrc.js"))
        .add(SOURCE.file("jest.config.js"), to("jest.config.js"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .add(SOURCE.file("vite.config.ts"), to("vite.config.ts"))
        .add(SOURCE.template("webapp/app/http/AxiosHttp.ts.mustache"), MAIN_DESTINATION.append("http/AxiosHttp.ts"))
        .batch(SOURCE.file("test/spec/http"), to("src/test/javascript/spec/http"))
          .template("AxiosHttp.spec.ts")
          .template("AxiosHttpStub.ts")
          .template("AxiosStub.ts")
          .and()
        .add(SOURCE.template("webapp/index.html"), to("src/main/webapp/index.html"))
        .batch(SOURCE.append("webapp/app"), MAIN_DESTINATION)
          .template("env.d.ts")
          .template("main.ts")
          .and()
        .batch(COMMON_PRIMARY_SOURCE.append("app"), MAIN_PRIMARY_DESTINATION.append("app"))
          .template("App.component.ts")
          .template("App.html")
          .template("App.vue")
          .template("index.ts")
          .and()
        .batch(IMAGE_SOURCE, to("src/main/webapp/content/images"))
          .file("JHipster-Lite-neon-green.png")
          .file("VueLogo.png")
          .and()
        .add(COMMON_PRIMARY_TEST_SOURCE.template("app/App.spec.ts"), COMMON_PRIMARY_TEST_DESTINATION.append("app/App.spec.ts"))
        .batch(COMMON_PRIMARY_SOURCE.append("homepage"), MAIN_PRIMARY_DESTINATION.append("homepage"))
          .template("Homepage.component.ts")
          .template("Homepage.html")
          .template("Homepage.vue")
          .template("index.ts")
          .and()
        .add(COMMON_PRIMARY_TEST_SOURCE.template("homepage/Homepage.spec.ts"), COMMON_PRIMARY_TEST_DESTINATION.append("homepage/Homepage.spec.ts"))
        .add(SOURCE.template("webapp/app/router/router.ts"), MAIN_DESTINATION.append("router/router.ts"))
        .add(SOURCE.template("test/spec/router/Router.spec.ts"), to("src/test/javascript/spec/router/Router.spec.ts"))
        .batch(SOURCE.append("webapp/app/common/domain"), MAIN_DESTINATION.append("common/domain"))
          .template("Logger.ts")
          .template("Message.ts")
          .and()
        .add(SOURCE.template("webapp/app/common/secondary/ConsoleLogger.ts"), MAIN_DESTINATION.append("common/secondary/ConsoleLogger.ts"))
        .add(SOURCE.template("test/spec/common/domain/Logger.fixture.ts"), to("src/test/javascript/spec/common/domain/Logger.fixture.ts"))
        .add(SOURCE.template("test/spec/common/secondary/ConsoleLogger.spec.ts"), to("src/test/javascript/spec/common/secondary/ConsoleLogger.spec.ts"))
        .and()
      .build();
    //@formatter:on
  }

  private String jestSonar(Indentation indentation) {
    return new StringBuilder()
      .append(indentation.spaces())
      .append("\"jestSonar\": {")
      .append(BREAK)
      .append(indentation.times(2))
      .append("\"reportPath\": \"target/test-results/jest\",")
      .append(BREAK)
      .append(indentation.times(2))
      .append("\"reportFile\": \"TESTS-results-sonar.xml\"")
      .append(BREAK)
      .append(indentation.spaces())
      .append("},")
      .toString();
  }

  public JHipsterModule buildPiniaModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDependency(packageName("pinia"), VUE)
        .addDependency(packageName("pinia-plugin-persist"), VUE)
        .addDevDependency(packageName("@pinia/testing"), VUE)
        .and()
      .mandatoryReplacements()
        .in("src/main/webapp/app/main.ts")
          .add(justLineBefore(text(IMPORT_NEEDLE)), piniaImports())
          .add(justLineBefore(text(PROVIDER_NEEDLE)), piniaProvider())
          .and()
        .and()
      .build();
    //@formatter:on
  }

  private String piniaImports() {
    return """
        import {createPinia} from 'pinia';
        import piniaPersist from 'pinia-plugin-persist'
        """;
  }

  private String piniaProvider() {
    return """
        const pinia = createPinia();
        pinia.use(piniaPersist);
        app.use(pinia);
        """;
  }
}
