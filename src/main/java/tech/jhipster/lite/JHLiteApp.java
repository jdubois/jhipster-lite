package tech.jhipster.lite;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.core.env.Environment;
import tech.jhipster.lite.common.domain.Generated;

@Generated(reason = "Not interesting and hard to test")
@SpringBootApplication(exclude = { MongoAutoConfiguration.class, MongoDataAutoConfiguration.class })
public class JHLiteApp {

  private static final Logger log = LoggerFactory.getLogger(JHLiteApp.class);

  private static final String SEPARATOR = "----------------------------------------------------------";
  protected static final String LF = "\n";

  public static void main(String[] args) {
    Environment env = SpringApplication.run(JHLiteApp.class, args).getEnvironment();
    logApplicationStartup(env);
  }

  private static void logApplicationStartup(Environment env) {
    String protocol = getProtocol(env.getProperty("server.ssl.key-store"));
    String serverPort = env.getProperty("server.port");
    String contextPath = getContextPath(env.getProperty("server.servlet.context-path"));
    String hostAddress = getHostAddress();

    String welcomeMessage = new StringBuilder()
      .append(LF)
      .append(SEPARATOR)
      .append(LF)
      .append(applicationRunning(env.getProperty("spring.application.name")))
      .append(accessUrlLocal(protocol, serverPort, contextPath))
      .append(accessUrlExternal(protocol, hostAddress, serverPort, contextPath))
      .append(profile(Arrays.toString(env.getActiveProfiles())))
      .append(SEPARATOR)
      .append(configServer(env.getProperty("configserver.status")))
      .toString();

    log.info(welcomeMessage);
  }

  private static String applicationRunning(String value) {
    return String.format("  Application '%s' is running!", value) + LF;
  }

  private static String accessUrlLocal(String protocol, String serverPort, String contextPath) {
    if (StringUtils.isBlank(serverPort)) {
      return "";
    }
    return String.format("  Local: \t%s://localhost:%s%s", protocol, serverPort, contextPath) + LF;
  }

  private static String accessUrlExternal(String protocol, String hostAddress, String serverPort, String contextPath) {
    if (StringUtils.isBlank(serverPort)) {
      return "";
    }
    return String.format("  External: \t%s://%s:%s%s", protocol, hostAddress, serverPort, contextPath) + LF;
  }

  private static String profile(String profiles) {
    return String.format("  Profile(s): \t%s", profiles) + LF;
  }

  private static String configServer(String configServerStatus) {
    if (StringUtils.isBlank(configServerStatus)) {
      return "";
    }
    return LF + String.format("  Config Server: %s", configServerStatus) + LF + SEPARATOR + LF;
  }

  private static String getProtocol(String value) {
    return Optional.ofNullable(value).map(key -> "https").orElse("http");
  }

  private static String getContextPath(String value) {
    return Optional.ofNullable(value).filter(StringUtils::isNotBlank).orElse("/");
  }

  private static String getHostAddress() {
    String hostAddress = "localhost";
    try {
      hostAddress = InetAddress.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      log.warn("The host name could not be determined, using `localhost` as fallback");
    }
    return hostAddress;
  }
}
