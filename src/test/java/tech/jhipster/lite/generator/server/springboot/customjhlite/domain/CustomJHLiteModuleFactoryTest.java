package tech.jhipster.lite.generator.server.springboot.customjhlite.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.ModuleFile;

@UnitTest
class CustomJHLiteModuleFactoryTest {

  private static final CustomJHLiteModuleFactory factory = new CustomJHLiteModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myapp")
      .put("serverPort", 9000)
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModuleWithFiles(module, pomFile(), mainAppFile())
      .hasFile("pom.xml")
      .containing(
        """
            <dependency>
              <groupId>tech.jhipster.lite</groupId>
              <artifactId>jhlite</artifactId>
              <version>${jhlite.version}</version>
            </dependency>
        """
      )
      .containing(
        """
            <dependency>
              <groupId>tech.jhipster.lite</groupId>
              <artifactId>jhlite</artifactId>
              <version>${jhlite.version}</version>
              <classifier>tests</classifier>
              <scope>test</scope>
              <type>test-jar</type>
            </dependency>
        """
      )
      .and()
      .hasFile("src/main/resources/config/application.properties")
      .containing("server.port=9000")
      .containing("application.exception.package=org.,java.,net.,javax.,com.,io.,de.,tech.jhipster.lite,com.jhipster.test")
      .containing("spring.jackson.default-property-inclusion=non_null")
      .and()
      .hasFile("src/test/resources/config/application.properties")
      .containing("server.port=0")
      .and()
      .hasFile("src/main/java/com/jhipster/test/MyappApp.java")
      .containing("import tech.jhipster.lite.JHLiteApp;")
      .containing("@SpringBootApplication(scanBasePackageClasses = { JHLiteApp.class, MyappApp.class })")
      .and()
      .hasFile("documentation/module-creation.md");
  }

  private ModuleFile mainAppFile() {
    return file("src/test/resources/projects/files/MainApp.java", "src/main/java/com/jhipster/test/MyappApp.java");
  }
}
