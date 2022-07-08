package tech.jhipster.lite.generator.server.springboot.logsspy.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class LogsSpyModuleFactory {

  private static final JHipsterSource SOURCE = from("server/springboot/logsspy");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Logs spy"), SOURCE.file("logs-spy.md"))
      .files()
        .add(SOURCE.template("LogsSpy.java.mustache"), toSrcTestJava().append(properties.basePackage().path()).append("LogsSpy.java"))
        .and()
      .build();
    //@formatter:on
  }
}
