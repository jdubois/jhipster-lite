package tech.jhipster.lite.generator.server.springboot.mvc.dummy.domain;

import static tech.jhipster.lite.generator.project.domain.DefaultConfig.PACKAGE_NAME;

import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectFile;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class DummyDomainService implements DummyService {

  public static final String SOURCE = "server/springboot/mvc/dummy";
  public static final String PATCH = "dummy.patch";

  private final ProjectRepository projectRepository;

  public DummyDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void applyDummyGitPatch(Project project) {
    project.addDefaultConfig(PACKAGE_NAME);
    String packagePath = project.getPackageNamePath().orElse("com/mycompany/myapp");
    project.addConfig("packagePath", packagePath);

    projectRepository.gitInit(project);
    projectRepository.template(ProjectFile.forProject(project).withSource(SOURCE, PATCH).withDestinationFolder(".jhipster"));
    projectRepository.gitApplyPatch(project, FileUtils.getPath(project.getFolder(), ".jhipster", PATCH));
  }
}
