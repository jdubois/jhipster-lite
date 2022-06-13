package tech.jhipster.lite.generator.server.springboot.database.sqlcommon.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.error.domain.MissingMandatoryValueException;
import tech.jhipster.lite.generator.buildtool.generic.domain.Dependency;

@UnitTest
class SQLCommonTest {

  @Test
  void shouldGetHikariDependency() {
    Dependency dependency = SQLCommon.hikariDependency();

    assertThat(dependency.getArtifactId()).isEqualTo("HikariCP");
    assertThat(dependency.getGroupId()).isEqualTo("com.zaxxer");
  }

  @Test
  void shouldGetSpringJpaDependency() {
    Dependency dependency = SQLCommon.springDataJpa();

    assertThat(dependency.getArtifactId()).isEqualTo("spring-boot-starter-data-jpa");
    assertThat(dependency.getGroupId()).isEqualTo("org.springframework.boot");
  }

  @Test
  void shouldGetSqlHibernateCoreDependency() {
    Dependency dependency = SQLCommon.sqlHibernateCore();

    assertThat(dependency.getArtifactId()).isEqualTo("hibernate-core");
    assertThat(dependency.getGroupId()).isEqualTo("org.hibernate");
  }

  @Test
  void shouldNotBuildSourceWithoutDatabase() {
    assertThatThrownBy(() -> SQLCommon.getSource(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldNotBuildSourceWithEmptyDatabase() {
    assertThatThrownBy(() -> SQLCommon.getSource(""))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldNotBuildSDatabasePathWithoutDatabase() {
    assertThatThrownBy(() -> SQLCommon.getDatabasePath(null))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldNotBuildDatabasePathWithEmptyDatabase() {
    assertThatThrownBy(() -> SQLCommon.getDatabasePath(""))
      .isExactlyInstanceOf(MissingMandatoryValueException.class)
      .hasMessageContaining("database");
  }

  @Test
  void shouldBuildSource() {
    assertThat(SQLCommon.getSource("anyDB")).isEqualTo("server/springboot/database/anyDB");
  }

  @Test
  void shouldBuildDatabasePath() {
    assertThat(SQLCommon.getDatabasePath("anyDB")).isEqualTo("technical/infrastructure/secondary/anyDB");
  }
}
