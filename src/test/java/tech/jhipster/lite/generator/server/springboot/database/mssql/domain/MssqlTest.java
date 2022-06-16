package tech.jhipster.lite.generator.server.springboot.database.mssql.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.Map;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;

@UnitTest
class MssqlTest {

  @Test
  void shouldGetDockerImageName() {
    assertThat(Mssql.getDockerImageName()).isEqualTo("mcr.microsoft.com/mssql/server");
  }

  @Test
  void shouldGetSpringProperties() {
    Map<String, Object> springProperties = Mssql.springProperties("baseName");

    assertThat(springProperties)
      .containsEntry("spring.datasource.url", "jdbc:sqlserver://localhost:1433;database=baseName;trustServerCertificate=true")
      .containsEntry("spring.datasource.driver-class-name", "com.microsoft.sqlserver.jdbc.SQLServerDriver")
      .containsEntry("spring.datasource.username", "SA")
      .containsEntry("spring.datasource.password", "yourStrong(!)Password")
      .containsEntry("spring.jpa.hibernate.ddl-auto", "update")
      .containsEntry("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.SQLServer2012Dialect")
      .containsEntry("spring.jpa.properties.hibernate.format_sql", true);
  }
}
