package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleAsserter;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
class ReactJwtModuleFactoryTest {

  private static final String APP_TSX = "src/main/webapp/app/common/primary/app/App.tsx";

  private static final ReactJwtModuleFactory factory = new ReactJwtModuleFactory();

  @Test
  void shouldBuildModuleOnNotStyledApp() {
    JHipsterModule module = factory.buildModule(properties());

    ModuleAsserter asserter = assertThatModuleWithFiles(module, packageJsonFile(), notStyledApp());

    assertReactApp(asserter);
  }

  private ModuleFile notStyledApp() {
    return file("src/test/resources/projects/react-app/App.tsx", APP_TSX);
  }

  @Test
  void shouldBuildModuleOnStyledApp() {
    JHipsterModule module = factory.buildModule(properties());

    ModuleAsserter asserter = assertThatModuleWithFiles(module, packageJsonFile(), styledApp(), appCss());

    assertReactApp(asserter);
    asserter
      .createFile("src/main/webapp/app/common/primary/app/App.css")
      .containing(
        """
               -moz-osx-font-smoothing: grayscale;
               display: flex;
               flex-direction: column;
               justify-content:center;
               align-items: center;
            """
      );
  }

  private ModuleFile styledApp() {
    return file("src/test/resources/projects/react-app/StyledApp.tsx", APP_TSX);
  }

  private ModuleFile appCss() {
    return file("src/test/resources/projects/react-app/App.css", "src/main/webapp/app/common/primary/app/App.css");
  }

  private JHipsterModuleProperties properties() {
    return JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();
  }

  private void assertReactApp(ModuleAsserter asserter) {
    asserter
      .createFile("package.json")
      .containing(nodeDependency("react-hook-form"))
      .containing(nodeDependency("axios"))
      .containing(nodeDependency("@nextui-org/react"))
      .containing(nodeDependency("sass"))
      .and()
      .createPrefixedFiles(
        "src/main/webapp",
        "app/common/services/storage.ts",
        "app/login/primary/loginForm/index.tsx",
        "app/login/primary/loginModal/index.tsx",
        "app/login/services/login.ts"
      )
      .createPrefixedFiles("src/main/webapp/app/login/primary/loginModal", "index.tsx", "interface.d.ts", "LoginModal.scss")
      .createPrefixedFiles(
        "src/test/javascript/spec",
        "login/services/login.test.ts",
        "login/primary/loginForm/index.test.tsx",
        "login/primary/loginModal/index.test.tsx",
        "common/services/storage.test.ts"
      )
      .createFile("src/main/webapp/app/common/primary/app/App.tsx")
      .containing("import LoginForm from '@/login/primary/loginForm';")
      .containing("<LoginForm />");
  }
}
