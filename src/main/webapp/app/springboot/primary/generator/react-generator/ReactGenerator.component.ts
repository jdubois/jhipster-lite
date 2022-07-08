import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { ReactService } from '@/springboot/domain/client/ReactService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { ProjectService } from '@/springboot/domain/ProjectService';

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
    const projectService = inject('projectService') as ProjectService;

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

    const addReactWithJWT = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await reactService
          .addWithJWT(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('React with authentication JWT successfully added'))
          .catch(error => alertBus.error(`Adding React with authentication JWT to project failed ${error}`));
      }
    };

    const addCypressForReact = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addCypress(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Cypress successfully added'))
          .catch(error => alertBus.error(`Adding Cypress to project failed ${error}`));
      }
    };
    const addPlaywrightForReact = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addPlaywright(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Playwright successfully added'))
          .catch(error => alertBus.error(`Adding Playwright to project failed ${error}`));
      }
    };

    return {
      selectorPrefix,
      isReactWithStyle,
      addReact,
      addReactWithJWT,
      addCypressForReact,
      addPlaywrightForReact,
      props,
    };
  },
});
