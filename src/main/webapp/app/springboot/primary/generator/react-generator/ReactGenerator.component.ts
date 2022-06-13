import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'ReactGeneratorComponent',

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
    const reactService = inject('reactService') as ReactService;

    const selectorPrefix = 'react-generator';
    const isReactWithStyle = ref<boolean>(false);

    const addReact = async (): Promise<void> => {
      if (props.project.folder !== '') {
        if (isReactWithStyle.value) {
          await reactService
            .addWithStyle(toProject(props.project as ProjectToUpdate))
            .then(() => alertBus.success('React with style successfully added'))
            .catch(error => alertBus.error(`Adding React with style to project failed ${error}`));
        } else {
          await reactService
            .add(toProject(props.project as ProjectToUpdate))
            .then(() => alertBus.success('React successfully added'))
            .catch(error => alertBus.error(`Adding React to project failed ${error}`));
        }
      }
    };

    return {
      selectorPrefix,
      isReactWithStyle,
      addReact,
      props,
    };
  },
});
