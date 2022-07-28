package tech.jhipster.lite.generator.project.domain;

import static tech.jhipster.lite.common.domain.FileUtils.getPath;

public class Constants {

  private Constants() {}

  public static final String MAIN_JAVA = getPath("src/main/java");
  public static final String MAIN_WEBAPP = getPath("src/main/webapp");
  public static final String MAIN_RESOURCES = getPath("src/main/resources");
  public static final String MAIN_DOCKER = getPath("src/main/docker");

  public static final String TEMPLATE_FOLDER = "generator";
  public static final String DEPENDENCIES_FOLDER = "dependencies";

  public static final String TEST_JAVA = getPath("src/test/java");
  public static final String TEST_RESOURCES = getPath("src/test/resources");
  public static final String TEST_JAVASCRIPT = getPath("src/test/javascript/spec");
  public static final String TEST_TEMPLATE_RESOURCES = getPath(TEST_RESOURCES, TEMPLATE_FOLDER);

  public static final String POM_XML = "pom.xml";
  public static final String BUILD_GRADLE_KTS = "build.gradle.kts";
  public static final String SETTINGS_GRADLE_KTS = "settings.gradle.kts";
  public static final String LIQUIBASE_MASTER_XML = "master.xml";
  public static final String PACKAGE_JSON = "package.json";
  public static final String DOCKERFILE = "Dockerfile";
  public static final String INTEGRATION_TEST = "IntegrationTest.java";
  public static final String TSCONFIG_JSON = "tsconfig.json";
  public static final String README_MD = "README.md";

  // Hexagonal Architecture
  public static final String DOMAIN = "domain";
  public static final String INFRASTRUCTURE = "infrastructure";
  public static final String CONFIG = "config";

  private static final String PRIMARY = "primary";
  private static final String SECONDARY = "secondary";
  public static final String INFRASTRUCTURE_SECONDARY = getPath(INFRASTRUCTURE, SECONDARY);

  private static final String TECHNICAL = "technical";
  public static final String TECHNICAL_INFRASTRUCTURE = getPath(TECHNICAL, INFRASTRUCTURE);
  public static final String TECHNICAL_INFRASTRUCTURE_PRIMARY = getPath(TECHNICAL_INFRASTRUCTURE, PRIMARY);
  public static final String TECHNICAL_INFRASTRUCTURE_SECONDARY = getPath(TECHNICAL_INFRASTRUCTURE, SECONDARY);
  public static final String TECHNICAL_INFRASTRUCTURE_CONFIG = getPath(TECHNICAL_INFRASTRUCTURE, CONFIG);
}
