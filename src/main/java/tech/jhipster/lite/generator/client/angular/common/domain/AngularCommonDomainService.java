package tech.jhipster.lite.generator.client.angular.common.domain;

import static tech.jhipster.lite.common.domain.WordUtils.COMA;
import static tech.jhipster.lite.common.domain.WordUtils.C_BRACKET;
import static tech.jhipster.lite.common.domain.WordUtils.O_BRACKET;
import static tech.jhipster.lite.common.domain.WordUtils.indent;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.ALLOWED_COMMON_JS_DEPENDENCIES_REGEX_LIST;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.ANGULAR_JSON_FILE_NAME;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.DECLARATIONS_REGEX;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.DECLARATIONS_WITH_ARRAY_VALUES_REGEX_LIST;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.DECORATOR_REGEX_LIST;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.ENV_VARIABLES_WITH_VALUES_REGEX_LIST;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.EXISTING_IMPORT_PATTERN;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.PROVIDERS_WITH_ARRAY_VALUES_REGEX_LIST;
import static tech.jhipster.lite.generator.client.angular.common.domain.AngularCommon.TEST_REGEX_FORMAT;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tech.jhipster.lite.common.domain.FileUtils;
import tech.jhipster.lite.error.domain.GeneratorException;
import tech.jhipster.lite.generator.project.domain.Project;

public class AngularCommonDomainService implements AngularCommonService {

  @Override
  public void addImports(Project project, String tsFilePath, String imports) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), tsFilePath);
    String fileContent = getFileContent(fullFilePath);

    String newFileContent = getLastImportInFile(fileContent)
      .map(lastImport -> fileContent.replace(lastImport, lastImport + project.getEndOfLine() + imports))
      .orElse(imports + project.getEndOfLine() + fileContent);

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addInExistingImport(Project project, String tsFilePath, String importToAdd, String existingImportName) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), tsFilePath);
    String fileContent = getFileContent(fullFilePath);

    String newFileContent = getFirstMatchInFile(List.of(String.format(EXISTING_IMPORT_PATTERN, existingImportName)), fileContent)
      .map(importPrefix -> appendValuesInList(fileContent, importPrefix, importToAdd, ""))
      .orElseThrow(() -> new GeneratorException("Cannot find declarations array in file " + fullFilePath));

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addConstants(Project project, String tsFilePath, String constants) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), tsFilePath);
    String fileContent = getFileContent(fullFilePath);
    String newFileContent = getFirstMatchInFile(DECORATOR_REGEX_LIST, fileContent)
      .map(decoratorPrefix -> fileContent.replace(decoratorPrefix, constants + project.getEndOfLine() + decoratorPrefix))
      .orElse(fileContent + project.getEndOfLine() + constants);

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addDeclarations(Project project, String tsFilePath, String declarations) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), tsFilePath);
    String fileContent = getFileContent(fullFilePath);
    String newFileContent = getFirstMatchInFile(DECLARATIONS_WITH_ARRAY_VALUES_REGEX_LIST, fileContent)
      .map(declarationsPrefix -> appendValuesInList(fileContent, declarationsPrefix, declarations, project.getEndOfLine()))
      .orElseThrow(() -> new GeneratorException("Cannot find declarations array in file " + fullFilePath));

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addProviders(Project project, String tsFilePath, String providers) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), tsFilePath);
    String fileContent = getFileContent(fullFilePath);

    String newFileContent = getFirstMatchInFile(PROVIDERS_WITH_ARRAY_VALUES_REGEX_LIST, fileContent)
      .map(providersPrefix -> appendValuesInList(fileContent, providersPrefix, providers, project.getEndOfLine()))
      .orElseGet(() -> appendProvidersAfterDeclarations(fileContent, fullFilePath, providers, project));

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addEnvVariables(Project project, String envFilePath, String values) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), envFilePath);
    String fileContent = getFileContent(fullFilePath);
    String newFileContent = getFirstMatchInFile(ENV_VARIABLES_WITH_VALUES_REGEX_LIST, fileContent)
      .map(prefixEnvVariables -> {
        String updatedEnvVariablesStr = prefixEnvVariables.stripTrailing();
        if (!updatedEnvVariablesStr.endsWith(COMA)) {
          updatedEnvVariablesStr += COMA;
        }
        updatedEnvVariablesStr += project.getEndOfLine() + values.stripTrailing() + project.getEndOfLine();
        return fileContent.replace(prefixEnvVariables, updatedEnvVariablesStr);
      })
      .orElseThrow(() -> new GeneratorException("Cannot find environnement const in " + fullFilePath));
    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public void addHtml(Project project, String htmlFilePath, String htmlToAdd, String htmlTagRegexToReplace) {
    replaceTextByRegex(project, htmlFilePath, htmlToAdd, htmlTagRegexToReplace);
  }

  @Override
  public void addTest(Project project, String specTsFilePath, String testToAdd, String afterTestName) {
    String testRegex = String.format(TEST_REGEX_FORMAT, afterTestName.replaceAll("\\s", "[\\\\s]+"));
    replaceTextByRegex(project, specTsFilePath, testToAdd, testRegex);
  }

  @Override
  public void addAllowedCommonJsDependenciesAngularJson(Project project, String libToAdd) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), ANGULAR_JSON_FILE_NAME);
    String fileContent = getFileContent(fullFilePath);
    String newFileContent = getFirstMatchInFile(ALLOWED_COMMON_JS_DEPENDENCIES_REGEX_LIST, fileContent)
      .map(dependenciesPrefix -> appendValuesInList(fileContent, dependenciesPrefix, libToAdd, project.getEndOfLine()))
      .orElseThrow(() -> new GeneratorException("Cannot find allowed common js dependencies array in file " + fullFilePath));

    writeInFile(fullFilePath, newFileContent, project);
  }

  @Override
  public List<String> getAngularModules() {
    return List.of(
      "BrowserAnimationsModule",
      "HttpClientModule",
      "MatMenuModule",
      "MatToolbarModule",
      "MatIconModule",
      "MatButtonModule",
      "MatButtonToggleModule",
      "BrowserModule",
      "AppRoutingModule"
    );
  }

  private String getFileContent(String fullFilePath) {
    String fileContent;
    try {
      fileContent = FileUtils.read(fullFilePath);
    } catch (IOException e) {
      throw new GeneratorException("Cannot read file " + fullFilePath + ": " + e.getMessage());
    }
    return fileContent;
  }

  private void writeInFile(String fullFilePath, String newFileContent, Project project) {
    try {
      FileUtils.write(fullFilePath, newFileContent, project.getEndOfLine());
    } catch (IOException e) {
      throw new GeneratorException("Cannot write content in file " + fullFilePath + ": " + e.getMessage());
    }
  }

  private Optional<String> getLastImportInFile(String fileContent) {
    Pattern pattern = Pattern.compile(AngularCommon.IMPORT_REGEX, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(fileContent);
    String lastMatchFound = null;
    while (matcher.find()) {
      lastMatchFound = matcher.group(1);
    }
    return Optional.ofNullable(lastMatchFound);
  }

  private Optional<String> getFirstMatchInFile(List<String> regexList, String fileContent) {
    return regexList
      .stream()
      .map(regex -> {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        return pattern.matcher(fileContent);
      })
      .filter(Matcher::find)
      .findFirst()
      .map(matcher -> matcher.group(1));
  }

  private String appendValuesInList(String fileContent, String prefixWithArrayValuesStr, String arrayValuesToAdd, String endOfLine) {
    StringBuilder updatedArrayValuesStr = new StringBuilder(prefixWithArrayValuesStr.stripTrailing());
    if (!prefixWithArrayValuesStr.trim().endsWith(O_BRACKET) && !updatedArrayValuesStr.toString().endsWith(COMA)) {
      updatedArrayValuesStr.append(COMA);
    }
    int prefixIdx = prefixWithArrayValuesStr.indexOf(prefixWithArrayValuesStr.trim());
    String spaceBeforePrefix = prefixWithArrayValuesStr.substring(0, prefixIdx);
    updatedArrayValuesStr.append(endOfLine).append(arrayValuesToAdd.stripTrailing()).append(endOfLine).append(spaceBeforePrefix);
    return fileContent.replace(prefixWithArrayValuesStr, updatedArrayValuesStr.toString());
  }

  private String appendProvidersAfterDeclarations(String fileContent, String fullFilePath, String providers, Project project) {
    String declarationsStr = getFirstMatchInFile(List.of(DECLARATIONS_REGEX), fileContent)
      .map(String::stripTrailing)
      .orElseThrow(() -> new GeneratorException("Missing declarations in file: " + fullFilePath));
    String newProvidersArrayStr =
      indent(1) +
      "providers: " +
      O_BRACKET +
      project.getEndOfLine() +
      providers.stripTrailing() +
      project.getEndOfLine() +
      indent(1) +
      C_BRACKET +
      COMA;
    String declarationsAndProvidersStr = declarationsStr.trim().endsWith(COMA) ? declarationsStr : declarationsStr + COMA;
    declarationsAndProvidersStr += project.getEndOfLine() + newProvidersArrayStr;
    return fileContent.replace(declarationsStr, declarationsAndProvidersStr);
  }

  private void replaceTextByRegex(Project project, String filePath, String textToAdd, String textToReplaceRegex) {
    String fullFilePath = FileUtils.getPath(project.getFolder(), filePath);
    String fileContent = getFileContent(fullFilePath);
    Pattern pattern = Pattern.compile(textToReplaceRegex, Pattern.MULTILINE);
    Matcher matcher = pattern.matcher(fileContent);
    if (!matcher.find()) {
      throw new GeneratorException("Cannot find " + textToReplaceRegex + " in file " + fullFilePath);
    }
    String textFound = matcher.group(1);
    String newFileContent = fileContent.replace(textFound, textFound.stripTrailing() + project.getEndOfLine() + textToAdd);
    writeInFile(fullFilePath, newFileContent, project);
  }
}
