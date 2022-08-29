<template src="./ModuleParameters.html"></template>

<script lang="ts" src="./ModuleParameters.component.ts"></script>

<style lang="scss">
$jhipster-lite-valid-parameters-color: #2d900f;
$jhipster-lite-invalid-mandatory-parameters-color: #900f0f;
$jhipster-lite-invalid-optional-parameters-color: #90670f;

.jhipster-module-parameters {
  display: flex;
  border-radius: 1em;

  &.selected {
    margin-top: 15px;
    padding: 10px;
  }

  &.not-selected {
    padding: 0 10px 0 5px;
    margin-left: 7px;
  }

  &.invalid-mandatory-parameter {
    background-color: $jhipster-lite-invalid-mandatory-parameters-color;
  }

  &.invalid-optional-parameter {
    background-color: $jhipster-lite-invalid-optional-parameters-color;
  }

  &.all-valid-parameters {
    background-color: $jhipster-lite-valid-parameters-color;
  }

  &--type.selected {
    width: 6em;
  }

  &--type.not-selected {
    width: auto;
    display: flex;
  }

  &--parameter.not-selected {
    display: none;
  }

  &--type-title.selected {
    display: block;
  }

  &--type-title.not-selected {
    display: none;
  }

  &--type-stats.not-selected {
    margin-left: 7px;
  }

  &--parameter.selected {
    margin-right: 15px;
    display: block;
  }

  &--parameter-key {
    font-weight: bold;
  }

  &--parameter-value {
    height: 1em;
  }
}
</style>
