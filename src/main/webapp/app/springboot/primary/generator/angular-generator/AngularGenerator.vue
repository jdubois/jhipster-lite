<template>
  <div id="v-pills-angular" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-settings-tab">
    <GeneratorButtonVue :label="'Generate Angular'" :service="'angular'" :selector-prefix="selectorPrefix" @click.prevent="addAngular" />
    <GeneratorButtonVue
      :label="'Add JWT'"
      :service="'angular-with-jwt'"
      :selector-prefix="selectorPrefix"
      @click.prevent="addAngularWithJWT"
    />
    <GeneratorButtonVue :label="'Add OAuth2'" :service="'angular-oauth2'" :selector-prefix="selectorPrefix" @click.prevent="addOauth2" />
    <GeneratorButtonVue :label="'Add Health'" :service="'angular-health'" :selector-prefix="selectorPrefix" @click.prevent="addHealth" />
    <GeneratorButtonVue
      :label="'Cypress'"
      :service="'react-cypress'"
      :selector-prefix="selectorPrefix"
      @click.prevent="addCypressForAngular"
    />
    <GeneratorButtonVue
      :label="'Playwright'"
      :service="'client-common-playwright'"
      :selector-prefix="selectorPrefix"
      @click.prevent="addPlaywrightForAngular"
    />
  </div>
</template>

<script lang="ts" src="./AngularGenerator.component.ts"></script>
ngularRepository.spec.ts
