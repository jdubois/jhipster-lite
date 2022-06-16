package tech.jhipster.lite.generator.client.angular.common.domain;

import java.util.List;
import tech.jhipster.lite.generator.project.domain.Project;

public interface AngularCommonService {
  void addImports(Project project, String tsFilePath, String imports);

  void addInExistingImport(Project project, String tsFilePath, String importToAdd, String existingImportName);

  void addConstants(Project project, String tsFilePath, String constants);

  void addDeclarations(Project project, String tsFilePath, String declarations);

  void addProviders(Project project, String tsFilePath, String providers);

  void addEnvVariables(Project project, String envFilePath, String values);

  void addHtml(Project project, String htmlFilePath, String htmlToAdd, String htmlTagRegexToReplace);

  void addTest(Project project, String specTsFilePath, String testToAdd, String afterTestName);

  void addAllowedCommonJsDependenciesAngularJson(Project project, String libToAdd);

  List<String> getAngularModules();
}
