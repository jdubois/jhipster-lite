package tech.jhipster.lite.npm.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.common.domain.ProjectFilesReader;
import tech.jhipster.lite.npm.domain.NpmVersion;
import tech.jhipster.lite.npm.domain.NpmVersionSource;
import tech.jhipster.lite.npm.domain.UnknownNpmPackageException;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSystemNpmVersionsTest {

  @Mock
  private ProjectFilesReader projectFiles;

  @InjectMocks
  private FileSystemNpmVersions npmVersions;

  @Test
  void shouldNotGetUnknownVersion() {
    mockProjectFiles();

    assertThatThrownBy(() -> npmVersions.get("unknown", NpmVersionSource.COMMON)).isExactlyInstanceOf(UnknownNpmPackageException.class);
  }

  @Test
  void shouldGetVersionFromSource() {
    mockProjectFiles();

    NpmVersion version = npmVersions.get("@types/node", NpmVersionSource.COMMON);

    assertThat(version).isEqualTo(new NpmVersion("17.0.43"));
  }

  private void mockProjectFiles() {
    when(projectFiles.readString("/generator/dependencies/common/package.json"))
      .thenReturn(
        """
        {
          "name": "jhlite-dependencies",
          "version": "0.0.0",
          "description": "JHipster Lite : used for Dependencies",
          "license": "Apache-2.0",
          "devDependencies": {
            "@playwright/test": "1.22.2",
            "@prettier/plugin-xml": "2.2.0",
            "@types/jest": "28.1.1",
            "@types/node": "17.0.43",
            "@typescript-eslint/eslint-plugin": "5.28.0",
            "@typescript-eslint/parser": "5.28.0",
            "cypress": "10.1.0",
            "eslint": "8.17.0",
            "eslint-import-resolver-typescript": "2.7.1",
            "eslint-plugin-cypress": "2.12.1",
            "eslint-plugin-import": "2.26.0",
            "eslint-plugin-prettier": "4.0.0",
            "husky": "8.0.1",
            "jasmine-core": "4.2.0",
            "jest": "27.5.1",
            "lint-staged": "13.0.1",
            "prettier": "2.7.0",
            "prettier-plugin-java": "1.6.2",
            "prettier-plugin-packagejson": "2.2.18",
            "ts-jest": "27.1.4",
            "typescript": "4.7.3"
          }
        }
        """
      );
  }
}
