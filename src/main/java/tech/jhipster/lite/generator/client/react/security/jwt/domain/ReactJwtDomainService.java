package tech.jhipster.lite.generator.client.react.security.jwt.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;
import static tech.jhipster.lite.generator.project.domain.Constants.MAIN_WEBAPP;

import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.packagemanager.npm.domain.NpmService;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class ReactJwtDomainService implements ReactJwtService {

  public static final String SOURCE = "client/react";
  public static final String SOURCE_APP = MAIN_WEBAPP + "/app/common/primary/app";
  public static final String APP = "App.tsx";

  private final ProjectRepository projectRepository;
  private final NpmService npmService;

  public ReactJwtDomainService(final ProjectRepository projectRepository, final NpmService npmService) {
    this.projectRepository = projectRepository;
    this.npmService = npmService;
  }

  @Override
  public void addLoginReact(final Project project) {
    addDependencies(project);
    addDevDependencies(project);
    addReactLoginFiles(project);
    updateReactFilesForJWT(project);
  }

  public void addDevDependencies(final Project project) {
    ReactJwt
      .devDependencies()
      .forEach(dependency ->
        npmService
          .getVersionInReact(dependency)
          .ifPresentOrElse(
            version -> npmService.addDevDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("DevDependency not found: " + dependency);
            }
          )
      );
  }

  public void addDependencies(final Project project) {
    ReactJwt
      .dependencies()
      .forEach(dependency ->
        npmService
          .getVersionInReact(dependency)
          .ifPresentOrElse(
            version -> npmService.addDependency(project, dependency, version),
            () -> {
              throw new GeneratorException("Dependency not found: " + dependency);
            }
          )
      );
  }

  public void updateReactFilesForJWT(final Project project) {
    var oldHtml = "import './App.css';";
    var newHtml = """
      import LoginForm from '@/login/primary/loginForm';

      import './App.css';
      """;

    projectRepository.replaceText(project, SOURCE_APP, APP, oldHtml, newHtml);

    oldHtml =
      """
              <p>
                Edit&nbsp;
                <code>src/main/webapp/app/common/primary/app/App.tsx</code> to test hot module replacement.
              </p>
            </div>
      """;
    newHtml =
      """
              <p>
                Edit&nbsp;
                <code>src/main/webapp/app/common/primary/app/App.tsx</code> to test hot module replacement.
              </p>
              <LoginForm />
            </div>
      """;

    projectRepository.replaceText(project, SOURCE_APP, APP, oldHtml, newHtml);

    oldHtml = "  -moz-osx-font-smoothing: grayscale;";
    newHtml =
      """
         -moz-osx-font-smoothing: grayscale;
         display: flex;
         flex-direction: column;
         justify-content:center;
         align-items: center;
      """;

    projectRepository.replaceText(project, SOURCE_APP, "App.css", oldHtml, newHtml);
  }

  public void addReactLoginFiles(final Project project) {
    var files = ReactJwt
      .reactJwtFiles()
      .entrySet()
      .stream()
      .flatMap(entry ->
        entry
          .getValue()
          .stream()
          .map(value ->
            ProjectFile.forProject(project).withSource(getPath(SOURCE, value), entry.getKey()).withDestination(value, entry.getKey())
          )
      )
      .toList();

    projectRepository.template(files);
  }
}
