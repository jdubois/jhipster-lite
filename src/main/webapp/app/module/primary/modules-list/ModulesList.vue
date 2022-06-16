<template src="./ModulesList.html"></template>

<script lang="ts" src="./ModulesList.component.ts"></script>

<style scoped>
.jhipster-modules-list {
  color: #29293b;
  max-width: 1200px;
  margin: auto;
}

.jhister-modules-title {
  font-size: 2em;
}

.jhipster-module {
  border-radius: 2em;
  border: 1px solid #29293b;
  margin: 1em 0;
  display: grid;
  grid-template-columns: 25% auto;
  grid-gap: 10px;
  grid-auto-rows: minmax(2em, auto);
  align-items: center;
}

.jhipster-module--slug {
  grid-column: 1 / 2;
  grid-row: 1;
  border-radius: 2em;
  padding: 0.5em 1.5em;
  margin-right: 0.5em;
  background-color: #29293b;
  color: #fff;
  font-weight: bold;
  text-align: center;
}

.jhipster-module--description {
  grid-column: 2 / 2;
  grid-row: 1;
}

.jhipster-module-category--name {
  font-size: 1.5em;
}
</style>
