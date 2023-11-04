import { defineComponent, inject, ref } from 'vue';
import { IconVue } from '@/common/primary/icon';
import { ThemeButtonVue } from '@/common/primary/theme-button';
import { ManagementRepository } from '@/module/domain/ManagementRepository';
import { ManagementInfo } from '@/module/domain/ManagementInfo';

export default defineComponent({
  name: 'Header',

  components: {
    IconVue,
    ThemeButtonVue,
  },

  setup() {
    const management = inject('management') as ManagementRepository;
    const selectorPrefix = 'header';
    const version = ref('');

    management
      .getInfo()
      .then((info: ManagementInfo) => {
        version.value = info?.git?.commit?.id?.describe;
      })
      .catch(error => console.error(error));

    return {
      selectorPrefix,
      version,
    };
  },
});
