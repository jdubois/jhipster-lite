package tech.jhipster.lite.generator.buildtool.gradle.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.Optional;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;

public class GradleDomainService implements GradleService {

  public static final String SOURCE = "buildtool/gradle";

  @Override
  public Optional<String> getGroup(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, BUILD_GRADLE_KTS), "group = " + DQ, DQ);
  }
}
