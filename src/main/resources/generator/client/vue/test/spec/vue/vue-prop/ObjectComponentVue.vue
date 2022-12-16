<template>
  <div></div>
</template>

<script lang="ts">
import { defineComponent } from 'vue';
import { objectType } from '@/vue/VueProp';

interface CustomObjectType {
  someString: string;
  someNumber: number;
}

export default defineComponent({
  name: 'ObjectComponentVue',
  props: {
    customObject: {
      type: objectType<CustomObjectType>(),
      required: true,
    },
  },
});
</script>
