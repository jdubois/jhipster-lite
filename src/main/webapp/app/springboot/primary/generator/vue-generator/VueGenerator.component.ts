import { defineComponent, inject } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { VueService } from '@/springboot/domain/client/VueService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'VueGeneratorComponent',

  components: {
    GeneratorButtonVue,
  },

  props: {
    project: {
      type: Object,
      required: true,
    },
  },

  setup(props) {
    const alertBus = inject('alertBus') as AlertBus;
    const vueService = inject('vueService') as VueService;

    const selectorPrefix = 'vue-generator';

    const addVue = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await vueService
          .add(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Vue successfully added'))
          .catch(error => alertBus.error(`Adding Vue to project failed ${error}`));
      }
    };

    return {
      selectorPrefix,
      addVue,
      props,
    };
  },
});
