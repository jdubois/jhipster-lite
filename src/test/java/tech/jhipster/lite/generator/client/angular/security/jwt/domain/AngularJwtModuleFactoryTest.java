package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class AngularJwtModuleFactoryTest {

  private static final AngularJwtModuleFactory factory = new AngularJwtModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .projectBaseName("jhipster")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, appRoutingFile(), appModuleFile())
      .createPrefixedFiles(
        "src/main/webapp/app/auth",
        "account.model.ts",
        "account.model.spec.ts",
        "account.service.ts",
        "account.service.spec.ts",
        "auth.interceptor.ts",
        "auth.interceptor.spec.ts",
        "auth-jwt.service.spec.ts"
      )
      .createPrefixedFiles(
        "src/main/webapp/app/login",
        "login.service.ts",
        "login.service.spec.ts",
        "login.model.ts",
        "login.component.css",
        "login.component.html",
        "login.component.ts",
        "login.module.ts",
        "login.route.ts"
      )
      .createFile("src/main/webapp/app/login/login.component.spec.ts")
      .containing(".toEqual('jhipster')")
      .and()
      .createFile("src/main/webapp/app/app-routing.module.ts")
      .containing(
        """
              {
                path: '',
                loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
              },
            """
      )
      .and()
      .createFile("src/main/webapp/app/app.module.ts")
      .containing("import { ReactiveFormsModule } from '@angular/forms';")
      .containing("import { AuthInterceptor } from './auth/auth.interceptor'")
      .containing(", ReactiveFormsModule]")
      .containing(
        """
              providers: [
                {
                  provide: HTTP_INTERCEPTORS,
                  useClass: AuthInterceptor,
                  multi: true,
                },
              ],
            """
      );
  }

  private static ModuleFile appRoutingFile() {
    return file("src/test/resources/projects/angular/app-routing.module.ts", "src/main/webapp/app/app-routing.module.ts");
  }

  private static ModuleFile appModuleFile() {
    return file("src/test/resources/projects/angular/app.module.ts", "src/main/webapp/app/app.module.ts");
  }
}
