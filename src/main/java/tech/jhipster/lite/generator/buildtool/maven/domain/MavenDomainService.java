package tech.jhipster.lite.generator.buildtool.maven.domain;

import static tech.jhipster.lite.common.domain.FileUtils.*;
import static tech.jhipster.lite.common.domain.WordUtils.*;
import static tech.jhipster.lite.generator.buildtool.maven.domain.Maven.*;
import static tech.jhipster.lite.generator.project.domain.Constants.*;
import static tech.jhipster.lite.generator.project.domain.DefaultConfig.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.Assert;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;
import tech.jhipster.lite.generator.project.domain.Project;
import tech.jhipster.lite.generator.project.domain.ProjectRepository;

public class MavenDomainService implements MavenService {

  public static final String SOURCE = "buildtool/maven";

  private final ProjectRepository projectRepository;

  public MavenDomainService(ProjectRepository projectRepository) {
    this.projectRepository = projectRepository;
  }

  @Override
  public void addDependency(Project project, Dependency dependency) {
    addDependency(project, dependency, null);
  }

  @Override
  public void addDependency(Project project, Dependency dependency, List<Dependency> exclusions) {
    addDependency(
      project,
      dependency,
      exclusions,
      dependency.getScope().filter(s -> s.equals("test")).map(s -> NEEDLE_DEPENDENCY_TEST).orElse(NEEDLE_DEPENDENCY)
    );
  }

  @Override
  public void addDependencyManagement(Project project, Dependency dependency) {
    addDependency(project, dependency, null, NEEDLE_DEPENDENCY_MANAGEMENT);
  }

  private void addDependency(Project project, Dependency dependency, List<Dependency> exclusions, String needle) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    int level = NEEDLE_DEPENDENCY_MANAGEMENT.equals(needle) ? 3 : 2;

    String dependencyNode = Maven.getDependencyHeader(dependency, indent).indent(level * indent);
    String dependencyRegexp = (REGEXP_PREFIX_MULTILINE + dependencyNode);

    if (!projectRepository.containsRegexp(project, "", POM_XML, dependencyRegexp)) {
      String newDependencyNode = Maven.getDependency(dependency, indent, exclusions).indent(level * indent);
      String dependencyWithNeedle = (newDependencyNode + indent(level, indent) + needle);

      projectRepository.replaceText(project, "", POM_XML, REGEXP_SPACE_STAR + needle, dependencyWithNeedle);
    }
  }

  @Override
  public void addProperty(Project project, String key, String value) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    int indent = (Integer) project.getConfig(PRETTIER_DEFAULT_INDENT).orElse(DEFAULT_INDENTATION);

    String pluginRegexp = Maven.getProperty(key, ".*");

    if (!projectRepository.containsRegexp(project, "", POM_XML, pluginRegexp)) {
      String propertyWithNeedle = Maven.getProperty(key, value) + LF + indent(2, indent) + NEEDLE_PROPERTIES;

      projectRepository.replaceText(project, "", POM_XML, NEEDLE_PROPERTIES, propertyWithNeedle);
    }
  }

  @Override
  public void deleteProperty(Project project, String key) {
    project.addDefaultConfig(PRETTIER_DEFAULT_INDENT);

    String propertyNode = Maven.getProperty(key, ".*") + LF;

    projectRepository.replaceText(project, "", POM_XML, propertyNode, "");
  }

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
