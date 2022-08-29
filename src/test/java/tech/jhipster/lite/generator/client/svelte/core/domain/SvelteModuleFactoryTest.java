package tech.jhipster.lite.generator.client.svelte.core.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
class SvelteModuleFactoryTest {

  private static final SvelteModuleFactory factory = new SvelteModuleFactory();

  @Test
  void shouldCreateSvelteModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildSvelteModule(properties);

    assertThatModuleWithFiles(module, packageJsonFile(), lintstagedFile())
      .createFile("package.json")
      .containing(nodeDependency("svelte-navigator"))
      .containing(nodeDependency("@babel/preset-env"))
      .containing(nodeDependency("@sveltejs/adapter-static"))
      .containing(nodeDependency("@sveltejs/kit"))
      .containing(nodeDependency("@testing-library/svelte"))
      .containing(nodeDependency("@testing-library/jest-dom"))
      .containing(nodeDependency("@types/jest"))
      .containing(nodeDependency("@typescript-eslint/eslint-plugin"))
      .containing(nodeDependency("@typescript-eslint/parser"))
      .containing(nodeDependency("babel-jest"))
      .containing(nodeDependency("babel-plugin-transform-vite-meta-env"))
      .containing(nodeDependency("eslint"))
      .containing(nodeDependency("eslint-config-prettier"))
      .containing(nodeDependency("eslint-plugin-svelte3"))
      .containing(nodeDependency("jest"))
      .containing(nodeDependency("jest-environment-jsdom"))
      .containing(nodeDependency("jest-transform-stub"))
      .containing(nodeDependency("jest-sonar-reporter"))
      .containing(nodeDependency("prettier"))
      .containing(nodeDependency("prettier-plugin-svelte"))
      .containing(nodeDependency("svelte"))
      .containing(nodeDependency("svelte-check"))
      .containing(nodeDependency("svelte-preprocess"))
      .containing(nodeDependency("svelte-jester"))
      .containing(nodeDependency("tslib"))
      .containing(nodeDependency("ts-jest"))
      .containing(nodeDependency("typescript"))
      .containing(nodeDependency("vite"))
      .containing("\"dev\": \"vite dev --port 9000\"")
      .containing("\"start\": \"vite dev --port 9000\"")
      .containing("\"build\": \"vite build\"")
      .containing("\"package\": \"vite package\"")
      .containing("\"preview\": \"vite preview\"")
      .containing("\"check\": \"svelte-check --tsconfig ./tsconfig.json\"")
      .containing("\"check:watch\": \"svelte-check --tsconfig ./tsconfig.json --watch\"")
      .containing("\"lint\": \"prettier --ignore-path .gitignore --check --plugin-search-dir=. . && eslint --ignore-path .gitignore .\"")
      .containing("\"format\": \"prettier --ignore-path .gitignore --write --plugin-search-dir=. .\"")
      .containing("\"test\": \"jest\"")
      .containing(
        "  \"jestSonar\": {\n    \"reportPath\": \"target/test-results/jest\",\n    \"reportFile\": \"TESTS-results-sonar.xml\"\n  },"
      )
      .containing("\"type\": \"module\"")
      .and()
      .createFiles(".eslintrc.cjs", "tsconfig.json", "svelte.config.js", "jest.config.cjs", "vite.config.js")
      .createPrefixedFiles("src/main/webapp", "app.html", "app.d.ts", "jest-setup.ts")
      .createPrefixedFiles("src/main/webapp/routes", "+page.svelte")
      .createPrefixedFiles("src/test/javascript/spec/common/primary/app", "App.spec.ts")
      .createPrefixedFiles("src/main/webapp/app/common/primary/app", "App.svelte")
      .createPrefixedFiles("src/main/webapp/assets", "JHipster-Lite-neon-orange.png")
      .createPrefixedFiles("src/main/webapp/assets", "svelte-logo.png")
      .createFiles(".lintstagedrc.cjs")
      .doNotCreateFiles(".lintstagedrc.js");
  }

  private ModuleFile lintstagedFile() {
    return file("src/test/resources/projects/files/.lintstagedrc.js", ".lintstagedrc.js");
  }
}
