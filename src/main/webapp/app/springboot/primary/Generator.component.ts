import { defineComponent, inject, ref } from 'vue';
import { ProjectToUpdate } from '@/springboot/primary/ProjectToUpdate';
import { AngularGeneratorVue } from '@/springboot/primary/generator/angular-generator';
import { ReactGeneratorVue } from '@/springboot/primary/generator/react-generator';
import { VueGeneratorVue } from '@/springboot/primary/generator/vue-generator';
import { SvelteGeneratorVue } from '@/springboot/primary/generator/svelte-generator';
import { SpringBootGeneratorVue } from '@/springboot/primary/generator/spring-boot-generator';
import { DefaultButtonVue } from '@/common/primary/default-button';
import { HeaderVue } from '@/springboot/primary/header';
import { IconVue } from '@/common/primary/icon';
import { ProjectGeneratorVue } from '@/springboot/primary/generator/project-generator';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';
import { History } from '@/common/domain/History';
import { ProjectService } from '../domain/ProjectService';
import { StoreGeneric } from 'pinia';

export default defineComponent({
  name: 'GeneratorComponent',
  components: {
    HeaderVue,
    IconVue,
    DefaultButtonVue,
    ProjectGeneratorVue,
    SpringBootGeneratorVue,
    AngularGeneratorVue,
    ReactGeneratorVue,
    SvelteGeneratorVue,
    VueGeneratorVue,
  },
  setup() {
    const projectHistoryService = inject('projectHistoryService') as ProjectHistoryService;
    const projectService = inject('projectService') as ProjectService;
    const globalWindow = inject('globalWindow') as Window;
    const projectStore = inject('projectStore') as StoreGeneric;
    const selectorPrefix = 'generator';

    const project = ref<ProjectToUpdate>({
      folder: '',
    });
    const language = ref<string>();
    const buildTool = ref<string>('maven');
    const setupTool = ref<string>();
    const server = ref<string>();
    const client = ref<string>();

    projectStore.$subscribe((_mutation, state) => {
      project.value.baseName = state.project.baseName ?? project.value.baseName;
      project.value.packageName = state.project.packageName ?? project.value.packageName;
      project.value.projectName = state.project.projectName ?? project.value.projectName;
      project.value.serverPort = state.project.serverPort ?? project.value.serverPort;
    });

    let timeoutId: number | undefined = undefined;
    const getCurrentProjectHistory = (): Promise<History> => projectHistoryService.get(project.value.folder);
    const getProjectDetails = (): Promise<void> => projectService.getProjectDetails(project.value.folder);
    const debounceGetProjectDetails = (): void => {
      if (timeoutId) globalWindow.clearTimeout(timeoutId);
      timeoutId = globalWindow.setTimeout(() => {
        getCurrentProjectHistory();
        getProjectDetails();
      }, 400);
    };

    return {
      project,
      language,
      buildTool,
      setupTool,
      server,
      client,
      selectorPrefix,
      getCurrentProjectHistory,
      getProjectDetails,
      debounceGetProjectDetails,
    };
  },
});
