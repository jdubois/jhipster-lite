package tech.jhipster.lite.generator.readme.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.readme.domain.ReadMeService;

@Service
public class ReadMeApplicationService {

  private final ReadMeService readMeService;

  public ReadMeApplicationService(ReadMeService readMeService) {
    this.readMeService = readMeService;
  }

  public void addSection(final Project project, final String header, final String body) {
    readMeService.addSection(project, header, body);
  }
}
