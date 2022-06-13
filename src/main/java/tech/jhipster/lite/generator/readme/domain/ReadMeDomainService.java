package tech.jhipster.lite.generator.readme.domain;

import static tech.jhipster.lite.generator.project.domain.Constants.README_MD;

import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class ReadMeDomainService implements ReadMeService {

  private final ProjectRepository projectRepository;

  public ReadMeDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addSection(final Project project, final String header, final String body) {
    if (!projectRepository.containsRegexp(project, "", README_MD, header)) {
      projectRepository.replaceText(
        project,
        "",
        README_MD,
        "<!-- jhipster-needle-readme -->",
        header + "\n\n" + body + "\n\n<!-- jhipster-needle-readme -->"
      );
    }
  }
}
