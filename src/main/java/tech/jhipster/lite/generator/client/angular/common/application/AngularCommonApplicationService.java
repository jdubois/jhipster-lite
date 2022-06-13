package tech.jhipster.lite.generator.client.angular.common.application;

import org.springframework.stereotype.Service;
import tech.jhipster.lite.generator.client.angular.common.domain.AngularCommonService;
import tech.jhipster.lite.generator.project.domain.Project;

@Service
public class AngularCommonApplicationService {

  private final AngularCommonService angularCommonService;

  public AngularCommonApplicationService(AngularCommonService angularCommonService) {
    this.angularCommonService = angularCommonService;
  }

  public void addImports(Project project, String filePath, String imports) {
    angularCommonService.addImports(project, filePath, imports);
  }

  public void addInExistingImport(Project project, String filePath, String imports, String existingImportName) {
    angularCommonService.addInExistingImport(project, filePath, imports, existingImportName);
  }

  public void addConstants(Project project, String filePath, String constants) {
    angularCommonService.addConstants(project, filePath, constants);
  }

  public void addDeclarations(Project project, String filePath, String declarations) {
    angularCommonService.addDeclarations(project, filePath, declarations);
  }

  public void addProviders(Project project, String filePath, String providers) {
    angularCommonService.addProviders(project, filePath, providers);
  }

  public void addEnvVariables(Project project, String envFilePath, String values) {
    angularCommonService.addEnvVariables(project, envFilePath, values);
  }

  public void addHtml(Project project, String htmlFilePath, String htmlToAdd, String htmlTagRegexToReplace) {
    angularCommonService.addHtml(project, htmlFilePath, htmlToAdd, htmlTagRegexToReplace);
  }

  public void addTest(Project project, String specTsFilePath, String testToAdd, String afterTestName) {
    angularCommonService.addTest(project, specTsFilePath, testToAdd, afterTestName);
  }

  public void addAllowedCommonJsDependenciesAngularJson(Project project, String libToAdd) {
    angularCommonService.addAllowedCommonJsDependenciesAngularJson(project, libToAdd);
  }
}
