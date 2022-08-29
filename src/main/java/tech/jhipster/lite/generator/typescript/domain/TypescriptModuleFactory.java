package tech.jhipster.lite.generator.typescript.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.from;
import static tech.jhipster.lite.module.domain.JHipsterModule.moduleBuilder;
import static tech.jhipster.lite.module.domain.JHipsterModule.packageName;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptCommand;
import static tech.jhipster.lite.module.domain.JHipsterModule.scriptKey;
import static tech.jhipster.lite.module.domain.JHipsterModule.to;
import static tech.jhipster.lite.module.domain.packagejson.VersionSource.COMMON;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class TypescriptModuleFactory {

  private static final JHipsterSource SOURCE = from("typescript");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .packageJson()
        .addDevDependency(packageName("typescript"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/eslint-plugin"), COMMON)
        .addDevDependency(packageName("@typescript-eslint/parser"), COMMON)
        .addDevDependency(packageName("eslint"), COMMON)
        .addDevDependency(packageName("eslint-import-resolver-typescript"), COMMON)
        .addDevDependency(packageName("eslint-plugin-import"), COMMON)
        .addDevDependency(packageName("eslint-plugin-prettier"), COMMON)
        .addDevDependency(packageName("jest"), COMMON)
        .addDevDependency(packageName("@types/jest"), COMMON)
        .addDevDependency(packageName("ts-jest"), COMMON)
        .addScript(scriptKey("test"), scriptCommand("jest"))
        .addScript(scriptKey("test:watch"), scriptCommand("jest --watch"))
        .addScript(scriptKey("test:watch:all"), scriptCommand("jest --watchAll"))
        .addScript(scriptKey("eslint:ci"), scriptCommand("eslint './**/*.{ts,js}'"))
        .addScript(scriptKey("eslint"), scriptCommand("eslint './**/*.{ts,js}' --fix"))
        .and()
      .files()
        .add(SOURCE.file(".eslintrc.js"), to(".eslintrc.js"))
        .add(SOURCE.file("jest.config.js"), to("jest.config.js"))
        .add(SOURCE.file("tsconfig.json"), to("tsconfig.json"))
        .and()
      .build();
    //@formatter:on
  }
}
