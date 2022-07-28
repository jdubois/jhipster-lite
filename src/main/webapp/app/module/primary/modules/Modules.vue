<template src="./Modules.html"></template>

<script lang="ts" src="./Modules.component.ts"></script>

<style lang="scss">
$jhipster-lite-primary-color: #0f5b90;
$jhipster-lite-secondary-color: #0f172a;
$jhipster-lite-line-color: #334155;
$jhipster-lite-primary-text-color: #ddeffc;
$jhipster-lite-primary-button-background-color: #3e8abf;
$jhipster-lite-primary-input-color: #fff;
$jhipster-lite-applied-module-color: #2f7b17;
$jhipster-lite-font-family: Inter serif;
$jhipster-lite-box-radius: 6px;

.jhipster-modules {
  font-family: $jhipster-lite-font-family;
  background-color: $jhipster-lite-secondary-color;
  color: $jhipster-lite-primary-text-color;
  display: flex;
  height: calc(100vh - 68.87px);
  flex-direction: row-reverse;
}

.jhipster-modules-form {
  border-left: 1px solid $jhipster-lite-line-color;
  background-color: $jhipster-lite-secondary-color;
  padding: 0 15px;
  width: 350px;
  flex-direction: column;
  justify-content: space-between;
  display: flex;

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

  &--download-project {
    align-self: flex-end;
    margin-bottom: 15px;
    width: 100%;
    background-color: $jhipster-lite-primary-button-background-color;
    border: 1px solid $jhipster-lite-line-color;
    color: $jhipster-lite-primary-input-color;
    border-radius: 6px;
    padding: 9px 17px;
  }
}

.jhipster-modules-list {
  flex-grow: 1;

  &--categories {
    height: calc(100vh - 70px - 68.87px);
    overflow-y: auto;
    flex-grow: 1;
    padding: 0 15px;
  }
}

.jhipster-modules-filters {
  margin: 15px 0;
  padding: 0 15px;
  height: 40px;
  display: flex;
  align-items: center;

  &--tags {
    margin-right: 10px;
  }

  &--filter {
    font-size: 1.4em;
    background-color: $jhipster-lite-secondary-color;
    color: $jhipster-lite-primary-input-color;
    border: 1px solid $jhipster-lite-line-color;
    border-radius: $jhipster-lite-box-radius;
    padding: 0 10px;
    flex-grow: 1;
  }

  &--displayed-modules-count {
    margin-left: 15px;
    background-color: $jhipster-lite-primary-color;
    padding: 3px 1em;
    border-radius: 1em;
  }
}

.jhipster-module-category {
  &--name {
    font-size: 1.4em;
    font-weight: 800;
  }
}

.jhipster-module {
  border: 1px solid $jhipster-lite-line-color;
  border-radius: $jhipster-lite-box-radius;
  margin-bottom: 10px;
  display: flex;

  &.selected {
    background-color: $jhipster-lite-primary-color;
  }

  &--content {
    cursor: pointer;
    flex-grow: 1;
    padding: 5px 10px;
  }

  &--content.not-selected {
    display: flex;
  }

  &--content.applied {
    background-color: $jhipster-lite-applied-module-color;
  }

  &--tags.not-selected,
  &--description.not-selected {
    display: none;
  }

  &--tags.selected {
    display: flex;
    margin: 5px 0;
  }

  &--description.selected {
    display: block;
  }

  &--tag {
    background-color: $jhipster-lite-secondary-color;
    font-style: italic;
    margin-right: 7px;
    padding: 0 0.5em;
    border-radius: 1em;
  }

  &--slug {
    font-weight: bold;
  }

  &--apply-button {
    background-color: transparent;
    border: 0;
    border-left: 1px solid $jhipster-lite-line-color;
    padding: 0 10px;
    color: $jhipster-lite-primary-input-color;
    font-weight: bold;
    font-size: 1.5em;
  }

  &--apply-button:disabled {
    color: #868686;
  }
}
</style>
