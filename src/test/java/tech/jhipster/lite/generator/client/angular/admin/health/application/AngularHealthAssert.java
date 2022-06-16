package tech.jhipster.lite.generator.client.angular.admin.health.application;

import static org.assertj.core.api.Assertions.assertThat;
import static tech.jhipster.lite.TestUtils.assertFileContent;
import static tech.jhipster.lite.TestUtils.assertFileExist;
import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import tech.jhipster.lite.generator.client.angular.admin.health.domain.AngularHealth;
import tech.jhipster.lite.generator.project.domain.Project;

public class AngularHealthAssert {

  private AngularHealthAssert() {}

  public static void assertAppHealth(Project project) {
    String rootPath = "src/main/webapp/";
    AngularHealth.angularHealthFiles().forEach((file, path) -> assertFileExist(project, getPath(rootPath + path, file)));
  }

  public static void assertUpdatedFiles(Project project) throws IOException {
    // app-routing.module.ts
    List<String> appRoutingModuleTsLines = Files.readAllLines(
      Path.of(getPath(project.getFolder(), "src/main/webapp/app/app-routing.module.ts"))
    );
    List<String> expectedContentLines =
      """
      {
        path: 'admin',
        loadChildren: () => import('./admin/admin-routing.module').then(m => m.AdminRoutingModule),
      },
      // jhipster-needle-angular-route""".lines()
        .toList();
    assertThat(appRoutingModuleTsLines).containsAll(expectedContentLines);

    // app.component.html
    List<String> appComponentHtmlLines = Files.readAllLines(
      Path.of(getPath(project.getFolder(), "src/main/webapp/app/app.component.html"))
    );
    expectedContentLines = """
          <a routerLink="admin/health" mat-menu-item><span>Health</span></a>
      """.lines().toList();
    assertThat(appComponentHtmlLines).containsAll(expectedContentLines);

    // app-routing.module.spec.ts
    List<String> appRoutingModuleSpecLines = Files.readAllLines(
      Path.of(getPath(project.getFolder(), "src/main/webapp/app/app-routing.module.spec.ts"))
    );
    expectedContentLines =
      """
        it('should navigate on admin endpoint', () => {
          router.navigateByUrl('/admin');
        });
      """.lines()
        .toList();
    assertThat(appRoutingModuleSpecLines).containsAll(expectedContentLines);

    // admin-routing.module.spec.ts
    List<String> appAdminRoutingModuleSpecLines = Files.readAllLines(
      Path.of(getPath(project.getFolder(), "src/main/webapp/app/admin/admin-routing.module.spec.ts"))
    );
    expectedContentLines =
      """
        it('should navigate on health endpoint', () => {
          router.navigateByUrl('/health');
        });
      """.lines()
        .toList();
    assertThat(appAdminRoutingModuleSpecLines).containsAll(expectedContentLines);
  }
}
