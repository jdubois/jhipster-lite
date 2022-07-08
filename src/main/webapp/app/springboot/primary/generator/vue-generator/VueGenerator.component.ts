import { defineComponent, inject } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { VueService } from '@/springboot/domain/client/VueService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';
import { ProjectService } from '@/springboot/domain/ProjectService';

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
    const projectService = inject('projectService') as ProjectService;

    const selectorPrefix = 'vue-generator';

    const addVue = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await vueService
          .add(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Vue successfully added'))
          .catch(error => alertBus.error(`Adding Vue to project failed ${error}`));
      }
    };

    const addCypressForVue = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addCypress(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Cypress successfully added'))
          .catch(error => alertBus.error(`Adding Cypress to project failed ${error}`));
      }
    };

    const addPlaywrightForVue = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addPlaywright(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Playwright successfully added'))
          .catch(error => alertBus.error(`Adding Playwright to project failed ${error}`));
      }
    };
    return {
      selectorPrefix,
      addVue,
      addCypressForVue,
      addPlaywrightForVue,
      props,
    };
  },
});
