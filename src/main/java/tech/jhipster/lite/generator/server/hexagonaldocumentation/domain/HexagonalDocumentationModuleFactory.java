package tech.jhipster.lite.generator.server.hexagonaldocumentation.domain;

import static tech.jhipster.lite.module.domain.JHipsterModule.*;

import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterSource;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

public class HexagonalDocumentationModuleFactory {

  private static final JHipsterSource SOURCE = from("server/documentation/hexagonal-application-service");

  public JHipsterModule buildModule(JHipsterModuleProperties properties) {
    //@formatter:off
    return moduleBuilder(properties)
      .documentation(documentationTitle("Hexagonal architecture"), SOURCE.file("hexagonal-application-service-architecture.md"))
      .files()
        .batch(SOURCE, to("documentation"))
          .addFile("hexagonal-flow.png")
          .addFile("hexagonal-global-schema.png")
          .and()
        .and()
      .build();
    //@formatter:on
  }
}
