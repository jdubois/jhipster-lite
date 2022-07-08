package tech.jhipster.lite.generator.client.svelte.core.domain;

import java.util.List;

public class Svelte {

  private Svelte() {}

  public static List<String> dependencies() {
    return List.of("svelte-navigator");
  }

  public static List<String> devDependencies() {
    return List.of(
      "@babel/preset-env",
      "@sveltejs/adapter-static",
      "@sveltejs/kit",
      "@testing-library/svelte",
      "@testing-library/jest-dom",
      "@types/jest",
      "@typescript-eslint/eslint-plugin",
      "@typescript-eslint/parser",
      "babel-jest",
      "babel-plugin-transform-vite-meta-env",
      "eslint",
      "eslint-config-prettier",
      "eslint-plugin-svelte3",
      "jest",
      "jest-environment-jsdom",
      "jest-transform-stub",
      "jest-sonar-reporter",
      "prettier",
      "prettier-plugin-svelte",
      "svelte",
      "svelte-check",
      "svelte-preprocess",
      "svelte-jester",
      "tslib",
      "ts-jest",
      "typescript",
      "vite"
    );
  }
}
