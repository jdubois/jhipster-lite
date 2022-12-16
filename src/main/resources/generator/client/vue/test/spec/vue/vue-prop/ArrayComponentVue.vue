<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { arrayType } from '@/vue/VueProp';

export default defineComponent({
  name: 'ArrayComponentVue',
  props: {
    strings: {
      type: arrayType<string>(),
      required: true,
    },
  },
});
</script>
