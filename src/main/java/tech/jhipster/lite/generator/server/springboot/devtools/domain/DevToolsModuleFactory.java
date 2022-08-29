package tech.jhipster.lite.generator.server.springboot.devtools.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.file.JHipsterSource;
import tech.jhipster.lite.module.domain.javabuild.GroupId;
import tech.jhipster.lite.module.domain.javaproperties.SpringProfile;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class DevToolsModuleFactory {

  private static final GroupId SPRING_GROUP = groupId("org.springframework.boot");

  private static final JHipsterSource SOURCE = from("server/springboot/devtools");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    Assert.notNull("properties", properties);

    //@formatter:off
    JHipsterModuleBuilder builder = moduleBuilder(properties)
      .context()
        .put("applicationName", properties.projectBaseName().capitalized())
        .and();
    //@formatter:on

    appendDependencies(builder);
    appendSpringProperties(builder);

    builder.documentation(documentationTitle("Dev tools"), SOURCE.append("devtools.md.mustache"));

    return builder.build();
  }

  private void appendDependencies(JHipsterModuleBuilder builder) {
    builder.javaDependencies().addDependency(SPRING_GROUP, artifactId("spring-boot-devtools"));
  }

  private void appendSpringProperties(JHipsterModuleBuilder builder) {
    builder
      .springMainProperties()
      .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue("false"))
      .set(propertyKey("spring.devtools.restart.enabled"), propertyValue("false"));

    builder
      .springMainProperties(new SpringProfile("local"))
      .set(propertyKey("spring.devtools.livereload.enabled"), propertyValue("true"))
      .set(propertyKey("spring.devtools.restart.enabled"), propertyValue("true"));
  }
}
