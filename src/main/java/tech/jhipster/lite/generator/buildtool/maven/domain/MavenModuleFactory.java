package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.common.domain.WordUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class MavenModuleFactory {

  private static final JHipsterSource SOURCE = from("buildtool/maven");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    return moduleBuilder(properties)
      .context()
        .put("dasherizedBaseName", WordUtils.kebabCase(properties.projectBaseName().get()))
        .and()
      .files()
        .add(SOURCE.template("pom.xml"), to("pom.xml"))
        .addExecutable(SOURCE.file("mvnw"), to("mvnw"))
        .addExecutable(SOURCE.file("mvnw.cmd"), to("mvnw.cmd"))
        .batch(SOURCE.append(".mvn/wrapper"), to(".mvn/wrapper"))
          .file("maven-wrapper.jar")
          .file("maven-wrapper.properties")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
