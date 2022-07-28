<template src="./ModuleProperties.html"></template>

<script lang="ts" src="./ModuleProperties.component.ts"></script>

<style lang="scss">
$jhipster-lite-valid-properties-color: #2d900f;
$jhipster-lite-invalid-mandatory-properties-color: #900f0f;
$jhipster-lite-invalid-optional-properties-color: #90670f;

.jhipster-module-properties {
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

  &.invalid-mandatory-property {
    background-color: $jhipster-lite-invalid-mandatory-properties-color;
  }

  &.invalid-optional-property {
    background-color: $jhipster-lite-invalid-optional-properties-color;
  }

  &.all-valid-properties {
    background-color: $jhipster-lite-valid-properties-color;
  }

  &--type.selected {
    width: 6em;
  }

  &--type.not-selected {
    width: auto;
    display: flex;
  }

  &--property.not-selected {
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

  &--property.selected {
    margin-right: 15px;
    display: block;
  }

  &--property-key {
    font-weight: bold;
  }

  &--property-value {
    height: 1em;
  }
}
</style>
