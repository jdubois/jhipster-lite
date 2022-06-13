package tech.jhipster.lite.generator.client.tools.playwright.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

import java.util.List;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class PlaywrightDomainService implements PlaywrightService {

  public static final String SOURCE = "client/common/playwright";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public PlaywrightDomainService(ProjectRepository projectRepository, NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void init(Project project) {
    addDevDependencies(project);
    addPlaywrightScripts(project);
    addPlaywrightFiles(project);
    addPlaywrightTestFiles(project);
    addPlaywrightPageObjectFiles(project);
  }

  public void addDevDependencies(Project project) {
    Playwright
      .devDependencies()
      .forEach(dependency ->
        npmService
          .getVersionInCommon(dependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("DevDependency not found: " + dependency);
            }
          )
      );
  }

  public void addPlaywrightScripts(Project project) {
    Playwright.playwrightScripts().forEach((name, cmd) -> npmService.addScript(project, name, cmd));
  }

  public void addPlaywrightFiles(Project project) {
    project.addConfig("serverPort", 8080);
    projectRepository.template(
      ProjectFile.forProject(project).withSource(FileUtils.getPath(SOURCE), "playwright.config.ts").withSameDestination()
    );
  }

  public void addPlaywrightTestFiles(Project project) {
    List<ProjectFile> files = Playwright
      .playwrightTestFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE, entry.getValue()), entry.getKey())
          .withDestinationFolder(entry.getValue())
      )
      .toList();

    projectRepository.template(files);
  }

  public void addPlaywrightPageObjectFiles(Project project) {
    List<ProjectFile> files = Playwright
      .playwrightPageObjectFiles()
      .entrySet()
      .stream()
      .map(entry ->
        ProjectFile
          .forProject(project)
          .withSource(getPath(SOURCE, entry.getValue()), entry.getKey())
          .withDestinationFolder(entry.getValue())
      )
      .toList();

    projectRepository.template(files);
  }
}
