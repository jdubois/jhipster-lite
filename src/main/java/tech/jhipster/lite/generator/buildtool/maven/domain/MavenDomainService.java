package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;

public class MavenDomainService implements MavenService {

  public static final String SOURCE = "buildtool/maven";

  private static final String GROUP_ID_BEGIN = "<groupId>";
  private static final String GROUP_ID_END = "</groupId>";

  private static final String NAME_BEGIN = "<name>";
  private static final String NAME_END = "</name>";

  @Override
  public Optional<String> getVersion(String name) {
    Assert.notBlank("name", name);

    String propertyTagIni = new StringBuilder().append("<").append(name).append(".version").append(">").toString();
    String propertyTagEnd = new StringBuilder().append("</").append(name).append(".version").append(">").toString();
    Pattern pattern = Pattern.compile(propertyTagIni + "(.*)" + propertyTagEnd);

    return FileUtils
      .readLineInClasspath(getPath(TEMPLATE_FOLDER, DEPENDENCIES_FOLDER, POM_XML), propertyTagIni)
      .map(readValue -> {
        Matcher matcher = pattern.matcher(readValue);
        if (matcher.find()) {
          return matcher.group(1);
        }
        return null;
      });
  }

  @Override
  public Optional<String> getGroupId(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, POM_XML), GROUP_ID_BEGIN, GROUP_ID_END);
  }

  @Override
  public Optional<String> getName(String folder) {
    Assert.notBlank("folder", folder);

    return FileUtils.getValueBetween(getPath(folder, POM_XML), NAME_BEGIN, NAME_END);
  }
}
