package tech.jhipster.lite.generator.server.springboot.database.mssql.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.server.springboot.database.mssql.domain.MssqlService;

@Service
public class MssqlApplicationService {

  private final MssqlService mssqlService;

  public MssqlApplicationService(MssqlService mssqlService) {
    this.mssqlService = mssqlService;
  }

  public void init(Project project) {
    mssqlService.init(project);
  }
}
