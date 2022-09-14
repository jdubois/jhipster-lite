<template src="./LandscapeModule.html"></template>

<script lang="ts" src="./LandscapeModule.component.ts"></script>

<style lang="scss">
.jhipster-landscape-module {
  border-radius: $jhipster-lite-box-radius;
  background: rgba($color: $jhipster-lite-secondary-color, $alpha: 0.7);
  border: 1px solid $jhipster-lite-line-color;

  &--slug {
    text-align: center;

    &.-extended {
      padding: 5px 10px;
      font-weight: bold;
    }

    &.-compacted {
      padding: 2px 3px;
    }
  }

  &--operation {
    border-top: 1px dotted $jhipster-lite-line-color;
    padding: 5px 10px;
    font-size: 0.8em;

    &.-compacted {
      display: none;
    }
  }

  &.-compacted {
    margin-bottom: 5px;
  }

  &.-extended {
    margin-bottom: 15px;
  }

  &.-selectable-highlighted {
    animation: $jhipster-lite-selectable-highlight-animation;
  }

  &.-not-selectable-highlighted,
  &.-highlighted-unselection {
    animation: $jhipster-lite-not-selectable-highlight-animation;
  }

  &.-selectable {
    cursor: pointer;
  }

  &.-not-selectable {
    cursor: not-allowed;
    border-style: dotted;
    background-color: rgba($color: $jhipster-lite-disabled-color, $alpha: 0.5);
  }

  &.-selected {
    cursor: pointer;
    background-color: $jhipster-lite-primary-color;
  }

  &.-applied::before {
    margin: 2px 0 0 3px;
    float: left;
    color: #4aaa2d;
    font-family: 'jhlite-icons';
    content: '\e807';
  }
}
</style>
