<template>
  <div id="v-pills-vue" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-vue-tab">
    <GeneratorButtonVue label="Generate Vue.js" service="vue" :selector-prefix="selectorPrefix" @click.prevent="addVue" />
    <GeneratorButtonVue :label="'Cypress'" :service="'react-cypress'" :selector-prefix="selectorPrefix" @click.prevent="addCypressForVue" />
    <GeneratorButtonVue
      :label="'Playwright'"
      :service="'client-common-playwright'"
      :selector-prefix="selectorPrefix"
      @click.prevent="addPlaywrightForVue"
    />
  </div>
</template>

<script lang="ts" src="./VueGenerator.component.ts"></script>
