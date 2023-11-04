package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.defaultModuleResourceBuilder;
import static tech.jhipster.lite.module.domain.resource.JHipsterModulesResourceFixture.emptyHiddenModules;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import tech.jhipster.lite.module.application.JHipsterModulesApplicationService;
import tech.jhipster.lite.module.domain.JHipsterModule;
import tech.jhipster.lite.module.domain.JHipsterModuleEvents;
import tech.jhipster.lite.module.domain.JHipsterModuleSlug;
import tech.jhipster.lite.module.domain.JHipsterModuleToApply;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.resource.JHipsterModulesResources;
import tech.jhipster.lite.module.infrastructure.secondary.git.GitTestUtil;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesFixture;
import tech.jhipster.lite.module.infrastructure.secondary.javadependency.JavaDependenciesReader;
import tech.jhipster.lite.module.infrastructure.secondary.npm.NpmVersionsFixture;
import tech.jhipster.lite.module.infrastructure.secondary.npm.NpmVersionsReader;
import tech.jhipster.lite.project.infrastructure.primary.JavaProjects;

public final class TestJHipsterModules {

  private static final Set<NpmVersionsReader> customNpmVersionsReaders = Collections.newSetFromMap(new ConcurrentHashMap<>());
  private static final Set<JavaDependenciesReader> customJavaDependenciesReaders = Collections.newSetFromMap(new ConcurrentHashMap<>());

  private TestJHipsterModules() {}

  public static void register(NpmVersionsReader npmVersionsReader) {
    assertThat(npmVersionsReader).as("Npm versions reader to register can't be null").isNotNull();

    customNpmVersionsReaders.add(npmVersionsReader);
  }

  public static void register(JavaDependenciesReader javaDependenciesReader) {
    assertThat(javaDependenciesReader).as("Java dependencies reader to register can't be null").isNotNull();

    customJavaDependenciesReaders.add(javaDependenciesReader);
  }

  public static void unregisterReaders() {
    customNpmVersionsReaders.clear();
    customJavaDependenciesReaders.clear();
  }

  static void apply(JHipsterModule module) {
    applyer(module).apply();
  }

  private static TestJHipsterModulesFinalApplyer applyer(JHipsterModule module) {
    return new TestJHipsterModulesApplyer(module);
  }

  private static class TestJHipsterModulesApplyer implements TestJHipsterModulesFinalApplyer {

    private final JHipsterModule module;
    private final JHipsterModuleSlug slug;
    private final JHipsterModulesApplicationService modules;

    private TestJHipsterModulesApplyer(JHipsterModule module) {
      this.module = module;
      this.slug = new JHipsterModuleSlug("test-module");
      this.modules = buildApplicationService(module);
    }

    private static JHipsterModulesApplicationService buildApplicationService(JHipsterModule module) {
      ProjectFiles filesReader = new FileSystemProjectFiles();

      FileSystemJHipsterModulesRepository modulesRepository = new FileSystemJHipsterModulesRepository(
        filesReader,
        NpmVersionsFixture.npmVersions(filesReader, customNpmVersionsReaders),
        mock(JavaProjects.class),
        new JHipsterModulesResources(
          List.of(defaultModuleResourceBuilder().slug("test-module").factory(properties -> module).build()),
          emptyHiddenModules()
        )
      );

      return new JHipsterModulesApplicationService(
        mock(JHipsterModuleEvents.class),
        modulesRepository,
        JavaDependenciesFixture.javaVersionsRepository(filesReader, customJavaDependenciesReaders),
        JavaDependenciesFixture.projectVersionsRepository(),
        GitTestUtil.gitRepository(),
        new FileSystemGeneratedProjectRepository()
      );
    }

    @Override
    public void apply() {
      modules.apply(new JHipsterModuleToApply(slug, module.properties()));
    }
  }

  public interface TestJHipsterModulesFinalApplyer {
    void apply();
  }
}
