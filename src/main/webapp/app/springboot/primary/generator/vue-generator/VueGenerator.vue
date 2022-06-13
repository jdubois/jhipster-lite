<template>
  <div id="v-pills-vue" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-vue-tab">
    <GeneratorButtonVue label="Generate Vue.js" service="vue" :selector-prefix="selectorPrefix" @click.prevent="addVue" />
  </div>
</template>

<script lang="ts" src="./VueGenerator.component.ts"></script>
