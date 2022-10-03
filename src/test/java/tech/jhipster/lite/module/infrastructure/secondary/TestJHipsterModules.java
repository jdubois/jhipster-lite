package tech.jhipster.lite.module.infrastructure.secondary;

import static org.mockito.Mockito.*;

import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.ProjectFilesReader;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture;
import tech.jhipster.lite.module.infrastructure.secondary.git.GitTestUtil;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesFixture;
import tech.jhipster.lite.module.infrastructure.secondary.npm.NpmVersionsFixture;
import tech.jhipster.lite.project.infrastructure.primary.JavaProjects;

public final class TestJHipsterModules {

  private TestJHipsterModules() {}

  static void apply(JHipsterModule module) {
    applyer().module(module).slug("test-module").apply();
  }

  static TestJHipsterModulesModuleApplyer applyer() {
    return new TestJHipsterModulesApplyer();
  }

  public static class TestJHipsterModulesApplyer
    implements TestJHipsterModulesModuleApplyer, TestJHipsterModulesSlugApplyer, TestJHipsterModulesFinalApplyer {

    private static final JHipsterModulesApplicationService modules = buildApplicationService();

    private JHipsterModule module;
    private JHipsterModuleSlug slug;

    private TestJHipsterModulesApplyer() {}

    private static JHipsterModulesApplicationService buildApplicationService() {
      ProjectFilesReader filesReader = new FileSystemProjectFilesReader();

      FileSystemJHipsterModulesRepository modulesRepository = new FileSystemJHipsterModulesRepository(
        filesReader,
        NpmVersionsFixture.npmVersions(filesReader),
        mock(JavaProjects.class),
        JHipsterModulesResourceFixture.moduleResources()
      );

      return new JHipsterModulesApplicationService(
        mock(JHipsterModuleEvents.class),
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader),
        JavaDependenciesFixture.projectVersionsRepository(),
        GitTestUtil.gitRepository()
      );
    }

    @Override
    public TestJHipsterModulesSlugApplyer module(JHipsterModule module) {
      Assert.notNull("module", module);

      this.module = module;

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
      modules.apply(new JHipsterModuleToApply(slug, module));
    }
  }

  public interface TestJHipsterModulesModuleApplyer {
    public TestJHipsterModulesSlugApplyer module(JHipsterModule module);
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
