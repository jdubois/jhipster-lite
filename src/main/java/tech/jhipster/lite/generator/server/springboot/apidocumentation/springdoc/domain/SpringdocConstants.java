package tech.jhipster.lite.generator.server.springboot.apidocumentation.springdoc.domain;

public class SpringdocConstants {

  public static final String API_TITLE_CONFIG_KEY = "apiTitle";
  public static final String API_DESCRIPTION_CONFIG_KEY = "apiDescription";
  public static final String API_LICENSE_NAME_CONFIG_KEY = "apiLicenseName";
  public static final String API_LICENSE_URL_CONFIG_KEY = "apiLicenseUrl";
  public static final String API_EXT_DOC_DESCRIPTION_CONFIG_KEY = "apiExternalDocDescription";
  public static final String API_EXT_DOC_URL_CONFIG_KEY = "apiExternalDocUrl";

  public static final String DEFAULT_API_TITLE = "Project API";
  public static final String DEFAULT_API_DESCRIPTION = "Project description API";
  public static final String DEFAULT_LICENSE_NAME = "No license";
  public static final String DEFAULT_LICENSE_URL = "";
  public static final String DEFAULT_EXT_DOC_DESCRIPTION = "Project Documentation";
  public static final String DEFAULT_EXT_DOC_URL = "";

  public static final String DEFAULT_SWAGGER_UI_SORT_VALUE = "alpha";
  public static final boolean DEFAULT_TRY_OUT_ENABLED = true;

  private SpringdocConstants() {
    // Cannot be instantiated
  }
}
