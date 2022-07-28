package tech.jhipster.lite.generator.client.angular.admin.health.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
class AngularHealthModuleFactoryTest {

  private static final AngularHealthModuleFactory factory = new AngularHealthModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture.propertiesBuilder(TestFileUtils.tmpDirForTest()).build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, appRouting(), appComponent(), appRoutingSpec())
      .createPrefixedFiles("src/main/webapp/app/admin", "admin-routing.module.ts", "admin-routing.module.spec.ts")
      .createPrefixedFiles("src/main/webapp/app/config", "application-config.service.spec.ts", "application-config.service.ts")
      .createPrefixedFiles(
        "src/main/webapp/app/admin/health",
        "health.component.css",
        "health.component.html",
        "health.component.ts",
        "health.component.spec.ts",
        "health.model.ts",
        "health.module.ts",
        "health.route.ts",
        "health.service.spec.ts",
        "health.service.ts"
      )
      .createPrefixedFiles(
        "src/main/webapp/app/admin/health/modal",
        "health-modal.component.css",
        "health-modal.component.html",
        "health-modal.component.ts",
        "health-modal.component.spec.ts"
      )
      .createFile("src/main/webapp/app/app-routing.module.ts")
      .containing(
        """
        {
          path: 'admin',
          loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
        },
        """
      )
      .and()
      .createFile("src/main/webapp/app/app.component.html")
      .containing("<a routerLink=\"admin/health\" mat-menu-item><span>Health</span></a>")
      .and()
      .createFile("src/main/webapp/app/app-routing.module.spec.ts")
      .containing(
        """
          it('should navigate on admin endpoint', () => {
            router.navigateByUrl('/admin');
          });
        """
      );
  }

  private ModuleFile appRouting() {
    return file("src/test/resources/projects/angular/app-routing.module.ts", "src/main/webapp/app/app-routing.module.ts");
  }

  private ModuleFile appComponent() {
    return file("src/test/resources/projects/angular/app.component.html", "src/main/webapp/app/app.component.html");
  }

  private ModuleFile appRoutingSpec() {
    return file("src/test/resources/projects/angular/app-routing.module.spec.ts", "src/main/webapp/app/app-routing.module.spec.ts");
  }
}
