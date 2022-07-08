import { defineComponent, inject } from 'vue';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { SetupService } from '@/springboot/domain/SetupService';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'SetupGeneratorComponent',

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
    const setupService = inject('setupService') as SetupService;

    const selectorPrefix = 'setup-generator';
    const addGithubActions = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await setupService
          .addGithubActions(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Github actions successfully added'))
          .catch(error => alertBus.error(`Adding Github actions to project failed ${error}`));
      }
    };
    return {
      selectorPrefix,
      addGithubActions,
    };
  },
});
