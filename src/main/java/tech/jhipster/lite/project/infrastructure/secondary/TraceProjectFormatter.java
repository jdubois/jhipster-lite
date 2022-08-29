package tech.jhipster.lite.project.infrastructure.secondary;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.jhipster.lite.project.domain.ProjectPath;

class TraceProjectFormatter implements ProjectFormatter {

  private static final Logger log = LoggerFactory.getLogger(TraceProjectFormatter.class);

  @Override
  public void format(ProjectPath path) {
    log.info("No npm installed, can't format project");
  }
}
