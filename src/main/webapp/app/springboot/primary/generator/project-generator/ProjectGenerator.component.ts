import { defineComponent, inject } from 'vue';
import { ProjectToUpdate, toProject } from '@/springboot/primary/ProjectToUpdate';
import { ProjectService } from '@/springboot/domain/ProjectService';
import { GeneratorButtonVue } from '@/springboot/primary/generator/generator-button';
import { AlertBus } from '@/common/domain/alert/AlertBus';

export default defineComponent({
  name: 'ProjectGeneratorComponent',

  components: {
    GeneratorButtonVue,
  },

  props: {
    project: {
      type: Object,
      required: true,
    },
    buildTool: {
      type: String,
      required: true,
    },
    setupTool: {
      type: String,
      required: true,
    },
  },

  setup(props) {
    const alertBus = inject('alertBus') as AlertBus;
    const projectService = inject('projectService') as ProjectService;

    const selectorPrefix = 'project-generator';

    const initProject = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .init(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Project successfully initialized'))
          .catch(error => alertBus.error(`Project initialization failed ${error}`));
      }
    };

    const addMaven = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addMaven(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Maven successfully added'))
          .catch(error => alertBus.error(`Adding Maven to project failed ${error}`));
      }
    };

    const addCodespacesSetup = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addCodespacesSetup(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Codespaces setup successfully added'))
          .catch(error => {
            alertBus.error(`Adding Codespaces setup to project failed ${error}`);
          });
      }
    };

    const addGitpodSetup = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addGitpodSetup(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Gitpod setup successfully added'))
          .catch(error => {
            alertBus.error(`Adding Gitpod setup to project failed ${error}`);
          });
      }
    };

    const addJaCoCo = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addJaCoCo(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('JaCoCo successfully added'))
          .catch(error => alertBus.error(`Adding JaCoCo to project failed ${error}`));
      }
    };

    const addSonarBackend = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addSonarBackend(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Sonar Backend successfully added'))
          .catch(error => alertBus.error(`Adding Sonar Backend to project failed ${error}`));
      }
    };

    const addSonarBackendFrontend = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addSonarBackendFrontend(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Sonar Backend+Frontend successfully added'))
          .catch(error => alertBus.error(`Adding Sonar Backend+Frontend to project failed ${error}`));
      }
    };

    const addJavaBase = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addJavaBase(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Java Base successfully added'))
          .catch(error => alertBus.error(`Adding Java Base to project failed ${error}`));
      }
    };

    const addFrontendMavenPlugin = async (): Promise<void> => {
      if (props.project.folder !== '') {
        await projectService
          .addFrontendMavenPlugin(toProject(props.project as ProjectToUpdate))
          .then(() => alertBus.success('Frontend Maven Plugin successfully added'))
          .catch(error => alertBus.error(`Adding Frontend Maven Plugin to project failed ${error}`));
      }
    };

    return {
      selectorPrefix,
      props,
      initProject,
      addMaven,
      addJaCoCo,
      addSonarBackend,
      addSonarBackendFrontend,
      addJavaBase,
      addFrontendMavenPlugin,
      addCodespacesSetup,
      addGitpodSetup,
    };
  },
});
