<template>
  <div id="v-pills-init" class="tab-pane fade show active" role="tabpanel" aria-labelledby="v-pills-home-tab">
    <div class="d-flex flex-column gap-3">
      <div>
        <GeneratorButtonVue :label="'Init'" :service="'initialization'" :selector-prefix="selectorPrefix" @click.prevent="initProject" />
      </div>
      <div>
        <GeneratorButtonVue
          v-if="buildTool === 'maven'"
          :label="'Maven'"
          :service="'maven-java'"
          :selector-prefix="selectorPrefix"
          @click.prevent="addMaven"
        />
        <GeneratorButtonVue
          v-if="setupTool === 'codespaces'"
          :label="'Codespaces'"
          :service="'codespaces-setup'"
          :data-selector="selectorPrefix + '.add-codespaces-setup-button'"
          @click.prevent="addCodespacesSetup"
        />
        <GeneratorButtonVue
          v-if="setupTool === 'gitpod'"
          :label="'Gitpod'"
          :service="'gitpod-setup'"
          :data-selector="selectorPrefix + '.add-gitpod-setup-button'"
          @click.prevent="addGitpodSetup"
        />
        <GeneratorButtonVue
          :label="'JaCoCo'"
          :service="'jacoco-check-minimal-coverage'"
          :selector-prefix="selectorPrefix"
          @click.prevent="addJaCoCo"
        />
        <GeneratorButtonVue
          :label="'Sonar Backend'"
          :service="'sonar-java-backend'"
          :selector-prefix="selectorPrefix"
          @click.prevent="addSonarBackend"
        />
        <GeneratorButtonVue
          :label="'Sonar Backend+Frontend'"
          :service="'sonar-java-backend-and-frontend'"
          :selector-prefix="selectorPrefix"
          @click.prevent="addSonarBackendFrontend"
        />
      </div>
      <div>
        <GeneratorButtonVue :label="'Java Base'" :service="'java-base'" :selector-prefix="selectorPrefix" @click.prevent="addJavaBase" />
      </div>
      <div>
        <GeneratorButtonVue
          :label="'Frontend Maven Plugin'"
          :service="'frontend-maven-plugin'"
          :selector-prefix="selectorPrefix"
          @click.prevent="addFrontendMavenPlugin"
        />
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./ProjectGenerator.component.ts"></script>
