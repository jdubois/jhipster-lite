<template src="./ProjectActions.html"></template>

<script lang="ts" src="./ProjectActions.component.ts"></script>

<style lang="scss">
.jhipster-modules-actions {
  &--download-project,
  &--format-project {
    margin-bottom: 15px;
    width: 100%;
    background-color: $jhipster-lite-primary-alternative-color;
    border: 1px solid $jhipster-lite-line-color;
    color: $jhipster-lite-primary-input-color;
    border-radius: 6px;
    padding: 9px 17px;
  }

  &--download-project:disabled,
  &--format-project:disabled {
    background-color: $jhipster-lite-disabled-color;
  }
}
</style>
