package tech.jhipster.lite.module.infrastructure.secondary;

import static tech.jhipster.lite.module.domain.JHipsterModulesFixture.*;
import static tech.jhipster.lite.module.infrastructure.secondary.JHipsterModulesAssertions.*;

import ch.qos.logback.classic.Level;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import tech.jhipster.lite.LogsSpy;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.JHipsterModule;

@UnitTest
@ExtendWith(LogsSpy.class)
class FileSystemJHipsterModulesRepositoryTest {

  private final LogsSpy logs;

  public FileSystemJHipsterModulesRepositoryTest(LogsSpy logs) {
    this.logs = logs;
  }

  @Test
  void shouldApplyModule() {
    JHipsterModule module = module();

    // @formatter:off
    assertThatModuleWithFiles(module, file("src/test/resources/projects/maven/pom.xml", "pom.xml"), packageJsonFile()) 
      .createFiles(
        "src/main/java/com/company/myapp/MyApp.java",
        "src/main/java/com/company/myapp/errors/Assert.java",
        "src/main/java/com/company/myapp/errors/AssertionException.java",
        "documentation/cucumber-integration.md",
        ".gitignore"
      )
      .createExecutableFiles(".husky/pre-commit")
      .createFile("src/main/java/com/company/myapp/MyApp.java")
        .containing("com.test.myapp")
        .and()
      .createFile("pom.xml")
      .containing("<dummy-dependency.version>4.5.8</dummy-dependency.version>")
      .notContaining("""
              <dependency>
                <groupId>net.logstash.logback</groupId>
                <artifactId>logstash-logback-encoder</artifactId>
              </dependency>
          """)
      .containing(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                </dependency>
          """)
      .containing(
          """
              <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <exclusions>
                  <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-tomcat</artifactId>
                  </exclusion>
                </exclusions>
              </dependency>
          """)
        .containing(
          """
                <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-dependencies</artifactId>
                  <version>${spring-boot.version}</version>
                  <scope>import</scope>
                  <type>pom</type>
                </dependency>
              </dependencies>
            </dependencyManagement>   
          """
        )
        .containing(
          """
              <dependency>
                <groupId>org.junit.jupiter</groupId>
                <artifactId>junit-jupiter-engine</artifactId>
                <version>${spring-boot.version}</version>
                <scope>test</scope>
                <optional>true</optional>
              </dependency>
          """
        )
        .containing("<spring-boot.version>")
        .containing("</spring-boot.version>")
        .containing("<artifactId>spring-boot-starter</artifactId>")
        .containing("<artifactId>problem-spring-web</artifactId>")
        .notContaining(
          """
              <dependency>
                <groupId>org.assertj</groupId>
                <artifactId>assertj-core</artifactId>
                <version>${assertj.version}</version>
                <scope>test</scope>
              </dependency>
          """
        )
        .containing("<maven-enforcer-plugin.version>")
        .containing(
            """
              <build>
                <plugins>
                  <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                  </plugin>
                  <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-enforcer-plugin</artifactId>
                  </plugin>
                </plugins>
                <pluginManagement>
                  <plugins>
                    <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                      <version>${spring-boot.version}</version>
                      <executions>
                        <execution>
                          <goals>
                            <goal>repackage</goal>
                          </goals>
                        </execution>
                      </executions>
                      <configuration>
                        <mainClass>${start-class}</mainClass>
                      </configuration>
                    </plugin>
                    <plugin>
                      <groupId>org.apache.maven.plugins</groupId>
                      <artifactId>maven-enforcer-plugin</artifactId>
                      <version>${maven-enforcer-plugin.version}</version>
                      <executions>
                        <execution>
                          <id>enforce-versions</id>
                          <goals>
                            <goal>enforce</goal>
                          </goals>
                        </execution>
                        <execution>
                          <id>enforce-dependencyConvergence</id>
                          <configuration>
                            <rules>
                              <DependencyConvergence/>
                            </rules>
                            <fail>false</fail>
                          </configuration>
                          <goals>
                            <goal>enforce</goal>
                          </goals>
                        </execution>
                      </executions>
                      <configuration>
                        <rules>
                          <requireMavenVersion>
                            <message>You are running an older version of Maven. JHipster requires at least Maven ${maven.version}</message>
                            <version>[${maven.version},)</version>
                          </requireMavenVersion>
                          <requireJavaVersion>
                            <message>You are running an incompatible version of Java. JHipster supports JDK 17.</message>
                            <version>[17,18)</version>
                          </requireJavaVersion>
                        </rules>
                      </configuration>
                    </plugin>
                  </plugins>
                </pluginManagement>
              </build>
            """
          )
        .and()
      .createFile("package.json")
        .containing("\"scripts\": {\n    \"serve\": \"tikui-core serve\"")
        .containing("\"dependencies\": {\n    \"@angular/animations\": \"")
        .containing("\"devDependencies\": {\n    \"@playwright/test\": \"")
        .and()
      .createFile("src/main/java/com/company/myapp/errors/Assert.java")
        .containing("Dummy replacement")
        .containing("Another dummy replacement")
        .containing("Dummy collection replacement")
        .containing("Another dummy collection replacement")
        .containing("// Dummy comment\n  public static class IntegerAsserter {")
        .and()
      .createFile("src/main/resources/config/application.properties")
        .containing("springdoc.swagger-ui.operationsSorter=alpha")
        .and()
      .createFile("src/main/resources/config/application-local.properties")
        .containing("springdoc.swagger-ui.tryItOutEnabled=false")
        .and()
      .createFile("src/test/resources/config/application.properties")
        .containing("springdoc.swagger-ui.operationsSorter=test")
        .and()
      .createFile("src/test/resources/config/application-local.properties")
        .containing("springdoc.swagger-ui.tryItOutEnabled=test")
        .and()
      .createFile("README.md")
        .containing("- [Cucumber integration](documentation/cucumber-integration.md)")
        .containing("This is a readme section");
    // @formatter:on

    assertPreActions();
    assertPostActions();
  }

  private void assertPreActions() {
    logs.shouldHave(Level.DEBUG, "Applying fixture module");
    logs.shouldHave(Level.DEBUG, "You shouldn't add this by default in your modules :D");
  }

  private void assertPostActions() {
    logs.shouldHave(Level.DEBUG, "Fixture module applied");
    logs.shouldHave(Level.DEBUG, "Applied on");
    logs.shouldHave(Level.DEBUG, System.getProperty("java.io.tmpdir"));
  }
}
