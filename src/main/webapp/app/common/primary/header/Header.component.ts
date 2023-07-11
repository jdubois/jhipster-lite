import { defineComponent } from 'vue';
import { IconVue } from '@/common/primary/icon';
import { ThemeButtonVue } from '@/common/primary/theme-button';

export default defineComponent({
  name: 'Header',

  components: {
    IconVue,
    ThemeButtonVue,
  },

  setup() {
    const selectorPrefix = 'header';

    return {
      selectorPrefix,
    };
  },
});
