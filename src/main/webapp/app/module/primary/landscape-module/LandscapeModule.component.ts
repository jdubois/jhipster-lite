import { LandscapeModule } from '@/module/domain/landscape/LandscapeModule';
import { defineComponent, PropType } from 'vue';

export default defineComponent({
  name: 'LandscapeComponentVue',
  props: {
    module: {
      type: Object as PropType<LandscapeModule>,
      required: true,
    },
    landscapeElements: {
      type: Object as PropType<Map<string, HTMLElement>>,
      required: true,
    },
    moduleFlavor: {
      type: String,
      required: true,
    },
  },
  emits: ['over', 'out', 'clicked'],
  setup(props, { emit }) {
    const moduleClass = (): string => {
      return props.moduleFlavor;
    };

    const emphasisizeModule = (): void => {
      emit('over');
    };

    const deEmphasisizeModule = (): void => {
      emit('out');
    };

    const clickedModule = (): void => {
      emit('clicked');
    };

    return {
      moduleClass,
      emphasisizeModule,
      deEmphasisizeModule,
      clickedModule,
    };
  },
});
