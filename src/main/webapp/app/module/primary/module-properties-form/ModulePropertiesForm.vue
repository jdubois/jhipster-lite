<template src="./ModulePropertiesForm.html"></template>

<script lang="ts" src="./ModulePropertiesForm.component.ts"></script>

<style lang="scss">
.jhipster-modules-properties-form {
  &--properties-title {
    font-size: 1.4em;
    margin-top: 15px;
  }

  &--commit-modules {
    margin: 10px 0;
  }

  &--commit-modules-label {
    margin-left: 10px;
    cursor: pointer;
  }

  &--field-line {
    align-self: flex-start;
    margin-bottom: 10px;
  }

  &--field {
    width: 100%;
    border: 1px solid $jhipster-lite-line-color;
    background-color: $jhipster-lite-secondary-color;
    color: $jhipster-lite-primary-input-color;
    border-radius: 10px;
    padding: 2px 7px;
  }

  &--field-description {
    font-style: italic;
  }

  &--field-key {
    font-weight: bold;
  }

  &--field-optional-marker {
    text-align: right;
    font-style: italic;
  }
}
</style>
