package tech.jhipster.lite.generator.client.angular.security.jwt.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.Indentation;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterDestination;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.domain.replacement.RegexNeedleAfterReplacer;
import tech.jhipster.lite.module.domain.replacement.RegexReplacer;
import tech.jhipster.lite.module.domain.replacement.TextNeedleBeforeReplacer;

public class AngularJwtModuleFactory {

  private static final TextNeedleBeforeReplacer ROUTE_NEEDLE = lineBeforeText("// jhipster-needle-angular-route");

  private static final String LOGIN_MODULE_ROUTE =
    """
      {
        path: '',
        loadChildren: () => import('./login/login.module').then(m => m.LoginModule),
      },
      """;

  private static final String MODULES_IMPORTS =
    """
      import { ReactiveFormsModule } from '@angular/forms';
      import { AuthInterceptor } from './auth/auth.interceptor';

      """;

  private static final RegexReplacer IMPORT_NEEDLE = regex("(imports: *\\[[^]]+)(\\s*\\])");

  private static final RegexNeedleAfterReplacer PROVIDERS_NEEDLE = lineAfterRegex("^ *bootstrap: *\\[[^]]*\\],");
  private static final String PROVIDERS =
    """
      providers: [
        {
          provide: HTTP_INTERCEPTORS,
          useClass: AuthInterceptor,
          multi: true,
        },
      ],
      """;
  private static final JHipsterSource SOURCE = from("client/angular/security/jwt/src/main/webapp/app");

  private static final JHipsterDestination APP_DESTINATION = to("src/main/webapp/app");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    Indentation indentation = properties.indentation();

    //@formatter:off
    return moduleBuilder(properties)
      .files()
        .batch(SOURCE.append("auth"), APP_DESTINATION.append("auth"))
          .addTemplate("account.model.ts")
          .addTemplate("account.model.spec.ts")
          .addTemplate("account.service.ts")
          .addTemplate("account.service.spec.ts")
          .addTemplate("auth.interceptor.ts")
          .addTemplate("auth.interceptor.spec.ts")
          .addTemplate("auth-jwt.service.ts")
          .addTemplate("auth-jwt.service.spec.ts")
          .and()
        .batch(SOURCE.append("login"), APP_DESTINATION.append("login"))
          .addTemplate("login.service.ts")
          .addTemplate("login.service.spec.ts")
          .addTemplate("login.model.ts")
          .addTemplate("login.component.css")
          .addTemplate("login.component.html")
          .addTemplate("login.component.spec.ts")
          .addTemplate("login.component.ts")
          .addTemplate("login.module.ts")
          .addTemplate("login.route.ts")
          .and()
        .and()
      .mandatoryReplacements()
        .in("src/main/webapp/app/app-routing.module.ts")
          .add(ROUTE_NEEDLE, LOGIN_MODULE_ROUTE.indent(indentation.spacesCount()))
          .and()
        .in("src/main/webapp/app/app.module.ts")
          .add(fileStart(), MODULES_IMPORTS)
          .add(IMPORT_NEEDLE, "$1" + indentation.spaces() + ", ReactiveFormsModule$2")
          .add(PROVIDERS_NEEDLE, StringUtils.chop(PROVIDERS.indent(indentation.spacesCount())))
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
