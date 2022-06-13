package tech.jhipster.lite.generator.server.springboot.cache.common.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import tech.jhipster.lite.generator.buildtool.generic.domain.BuildToolService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class SpringBootCacheDomainService implements SpringBootCacheService {

  public static final String SOURCE = "server/springboot/cache/common";
  public static final String DESTINATION = "technical/infrastructure/secondary/cache";

  private final BuildToolService buildToolService;
  private final ProjectRepository projectRepository;

  public SpringBootCacheDomainService(BuildToolService buildToolService, ProjectRepository projectRepository) {
    this.buildToolService = buildToolService;
    this.projectRepository = projectRepository;
  }

  @Override
  public void addDependencies(Project project) {
    buildToolService.addDependency(project, SpringBootCache.springBootStarterCacheDependency());
  }

  @Override
  public void addEnableCaching(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    project.addDefaultConfig(BASE_NAME);
    String packageNamePath = project.getPackageNamePath().orElse(getPath("com/mycompany/myapp"));

    templateToCache(project, packageNamePath, "src", "CacheConfiguration.java", MAIN_JAVA);
  }

  private void templateToCache(Project project, String source, String type, String sourceFilename, String destination) {
    projectRepository.template(
      ProjectFile
        .forProject(project)
        .withSource(getPath(SOURCE, type), sourceFilename)
        .withDestinationFolder(getPath(destination, source, DESTINATION))
    );
  }
}
