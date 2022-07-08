<template>
  <div id="v-pills-setup" class="tab-pane fade" role="tabpanel" aria-labelledby="v-pills-profile-tab">
    <div class="d-flex flex-column gap-3"></div>
    <div>
      <GeneratorButtonVue
        :label="'Github Actions'"
        :service="'github-actions'"
        :selector-prefix="selectorPrefix"
        @click.prevent="addGithubActions"
      />
    </div>
  </div>
</template>

<script lang="ts" src="./SetupGenerator.component.ts"></script>
