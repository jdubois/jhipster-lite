package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.TestFileUtils;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModulesFixture;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;

@UnitTest
class MavenModuleFactoryTest {

  private static final MavenModuleFactory factory = new MavenModuleFactory();

  @Test
  void shouldBuildModule() {
    JHipsterModuleProperties properties = JHipsterModulesFixture
      .propertiesBuilder(TestFileUtils.tmpDirForTest())
      .basePackage("com.jhipster.test")
      .projectBaseName("myApp")
      .projectName("JHipster test")
      .build();

    JHipsterModule module = factory.buildModule(properties);

    assertThatModule(module)
      .createFile("pom.xml")
      .containing("<groupId>com.jhipster.test</groupId>")
      .containing("<artifactId>my-app</artifactId>")
      .containing("<name>myApp</name>")
      .containing("<description>JHipster test</description>")
      .and()
      .createExecutableFiles("mvnw", "mvnw.cmd")
      .createPrefixedFiles(".mvn/wrapper", "maven-wrapper.jar", "maven-wrapper.properties");
  }
}
