import { defineComponent, inject } from 'vue';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'AngularGeneratorComponent',

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
    const angularService = inject('angularService') as AngularService;

    const selectorPrefix = 'angular-generator';

    const addAngular = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await angularService
          .add(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Angular successfully added'))
          .catch(error => alertBus.error(`Adding Angular to project failed ${error}`));
      }
    };

    const addAngularWithJWT = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await angularService
          .addWithJWT(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Angular with authentication JWT successfully added'))
          .catch(error => alertBus.error(`Adding Angular with authentication JWT to project failed ${error}`));
      }
    };

    const addOauth2 = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await angularService
          .addOauth2(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('OAuth2 successfully added'))
          .catch(error => alertBus.error(`Adding Oauth2 to project failed ${error}`));
      }
    };

    return {
      selectorPrefix,
      addAngular,
      addAngularWithJWT,
      addOauth2,
      props,
    };
  },
});
