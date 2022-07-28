package tech.jhipster.lite.module.infrastructure.secondary;

import static org.mockito.Mockito.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.gradle.domain.GradleModuleFactory;
import tech.jhipster.lite.generator.buildtool.maven.domain.MavenModuleFactory;
import tech.jhipster.lite.generator.client.angular.core.domain.AngularModuleFactory;
import tech.jhipster.lite.generator.client.react.core.domain.ReactCoreModulesFactory;
import tech.jhipster.lite.generator.init.domain.InitModuleFactory;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.core.domain.SpringBootCoreModuleFactory;
import tech.jhipster.lite.generator.server.springboot.mvc.web.domain.SpringBootMvcsModulesFactory;
import tech.jhipster.lite.generator.server.springboot.mvc.zalandoproblem.domain.ZalandoProblemsModuleFactory;
import tech.jhipster.lite.git.domain.GitRepository;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.properties.JHipsterModuleProperties;
import tech.jhipster.lite.npm.infrastructure.secondary.FileSystemNpmVersions;
import tech.jhipster.lite.projectfile.infrastructure.secondary.FileSystemProjectFilesReader;

public final class TestJHipsterModules {

  private static final InitModuleFactory initModules = new InitModuleFactory(mock(GitRepository.class));
  private static final MavenModuleFactory mavenModules = new MavenModuleFactory();
  private static final GradleModuleFactory gradleModules = new GradleModuleFactory();
  private static final AngularModuleFactory angularModules = new AngularModuleFactory();
  private static final ReactCoreModulesFactory reactModules = new ReactCoreModulesFactory();
  private static final SpringBootCoreModuleFactory springBootModules = new SpringBootCoreModuleFactory();
  private static final SpringBootMvcsModulesFactory mvcModules = new SpringBootMvcsModulesFactory();
  private static final ZalandoProblemsModuleFactory zalandoProblemsModules = new ZalandoProblemsModuleFactory();

  private TestJHipsterModules() {}

  public static void applyInit(Project project) {
    applyer().module(initModules.buildFullModule(projectProperties(project))).properties(projectProperties(project)).slug("init").apply();
  }

  public static void applyMaven(Project project) {
    applyer()
      .module(mavenModules.buildModule(projectProperties(project)))
      .properties(projectProperties(project))
      .slug("maven-java")
      .apply();
  }

  public static void applyGradle(Project project) {
    applyer().module(gradleModules.buildModule(projectProperties(project))).properties(projectProperties(project)).slug("gradle").apply();
  }

  public static void applyAngular(Project project) {
    applyer().module(angularModules.buildModule(projectProperties(project))).properties(projectProperties(project)).slug("angular").apply();
  }

  public static void applyReact(Project project) {
    applyer()
      .module(reactModules.buildModuleWithStyle(projectProperties(project)))
      .properties(projectProperties(project))
      .slug("react")
      .apply();
  }

  public static void applySpringBootCore(Project project) {
    JHipsterModuleProperties properties = projectProperties(project);

    applyer().module(springBootModules.buildModule(properties)).properties(properties).slug("springboot").apply();
  }

  public static void applyTomcat(Project project) {
    applyer()
      .module(mvcModules.buildTomcatModule(projectProperties(project)))
      .properties(projectProperties(project))
      .slug("springboot-tomcat")
      .apply();
  }

  public static void applyZalandoProblems(Project project) {
    applyer()
      .module(zalandoProblemsModules.buildModule(projectProperties(project)))
      .properties(projectProperties(project))
      .slug("zalando-problems")
      .apply();
  }

  private static JHipsterModuleProperties projectProperties(Project project) {
    return new JHipsterModuleProperties(project.getFolder(), false, project.getConfig());
  }

  public static void apply(JHipsterModule module) {
    applyer().module(module).properties(JHipsterModuleProperties.defaultProperties(module.projectFolder())).slug("test-module").apply();
  }

  public static TestJHipsterModulesModuleApplyer applyer() {
    return new TestJHipsterModulesApplyer();
  }

  public static class TestJHipsterModulesApplyer
    implements
      TestJHipsterModulesModuleApplyer,
      TestJHipsterModulesPropertiesApplyer,
      TestJHipsterModulesSlugApplyer,
      TestJHipsterModulesFinalApplyer {

    private static final JHipsterModulesApplicationService modules = buildApplicationService();

    private JHipsterModule module;
    private JHipsterModuleProperties properties;
    private JHipsterModuleSlug slug;

    private TestJHipsterModulesApplyer() {}

    private static JHipsterModulesApplicationService buildApplicationService() {
      FileSystemProjectFilesReader filesReader = new FileSystemProjectFilesReader();

      FileSystemJHipsterModulesRepository modulesRepository = new FileSystemJHipsterModulesRepository(
        filesReader,
        new FileSystemNpmVersions(filesReader)
      );

      return new JHipsterModulesApplicationService(
        modulesRepository,
        mock(JHipsterModuleEvents.class),
        new FileSystemCurrentJavaDependenciesVersionsRepository(filesReader),
        new FileSystemProjectJavaDependenciesRepository(),
        mock(GitRepository.class)
      );
    }

    @Override
    public TestJHipsterModulesPropertiesApplyer module(JHipsterModule module) {
      Assert.notNull("module", module);

      this.module = module;

      return this;
    }

    @Override
    public TestJHipsterModulesSlugApplyer properties(JHipsterModuleProperties properties) {
      Assert.notNull("properties", properties);

      this.properties = properties;

      return this;
    }

    @Override
    public TestJHipsterModulesFinalApplyer slug(JHipsterModuleSlug slug) {
      Assert.notNull("slug", slug);

      this.slug = slug;

      return this;
    }

    @Override
    public void apply() {
      modules.apply(new JHipsterModuleToApply(properties, slug, module));
    }
  }

  public interface TestJHipsterModulesModuleApplyer {
    public TestJHipsterModulesPropertiesApplyer module(JHipsterModule module);
  }

  public interface TestJHipsterModulesPropertiesApplyer {
    TestJHipsterModulesSlugApplyer properties(JHipsterModuleProperties properties);
  }

  public interface TestJHipsterModulesSlugApplyer {
    TestJHipsterModulesFinalApplyer slug(JHipsterModuleSlug slug);

    default TestJHipsterModulesFinalApplyer slug(String slug) {
      return slug(new JHipsterModuleSlug(slug));
    }
  }

  public interface TestJHipsterModulesFinalApplyer {
    void apply();
  }
}
