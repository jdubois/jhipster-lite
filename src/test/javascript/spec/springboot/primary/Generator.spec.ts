import { GeneratorVue } from '@/springboot/primary';
import { shallowMount, VueWrapper } from '@vue/test-utils';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';
import { ProjectService } from '@/springboot/domain/ProjectService';
import { Project } from '@/springboot/domain/Project';
import { stubProjectHistoryService } from '../../common/domain/ProjectHistoryService.fixture';
import sinon, { SinonStub } from 'sinon';
import { stubProjectService } from '../domain/ProjectService.fixture';
import { ProjectStoreFixture, stubProjectStore } from './ProjectStore.fixture';

let wrapper: VueWrapper;
let component: any;

interface WrapperOptions {
  projectHistoryService: ProjectHistoryService;
  projectService: ProjectService;
  globalWindow: WindowStub;
  projectStore: ProjectStoreFixture;
}

interface WindowStub {
  clearTimeout: SinonStub;
  setTimeout: SinonStub;
}

const stubGlobalWindow = (): WindowStub =>
  ({
    clearTimeout: sinon.stub(),
    setTimeout: sinon.stub(),
  } as WindowStub);

const wrap = (wrapperOptions?: Partial<WrapperOptions>) => {
  const { projectHistoryService, projectService, globalWindow, projectStore }: WrapperOptions = {
    projectHistoryService: stubProjectHistoryService(),
    globalWindow: stubGlobalWindow(),
    projectService: stubProjectService(),
    projectStore: stubProjectStore(),
    ...wrapperOptions,
  };
  wrapper = shallowMount(GeneratorVue, {
    global: {
      provide: {
        projectHistoryService,
        projectService,
        globalWindow,
        projectStore,
      },
    },
  });
  component = wrapper.vm;
};

describe('Generator', () => {
  it('should exist', () => {
    wrap();

    expect(wrapper.exists()).toBe(true);
  });

  it('should get project history and details with current project folder', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const projectService = stubProjectService();
    wrap({ projectHistoryService, projectService });

    const projectPathInput = wrapper.find('#path');
    await projectPathInput.setValue('path');
    component.getCurrentProjectHistory();
    component.getProjectDetails();

    const path = projectHistoryService.get.getCall(0).args[0];
    expect(path).toEqual('path');
    const detailsPath = projectService.getProjectDetails.getCall(0).args[0];
    expect(detailsPath).toEqual('path');
  });

  it('should delay get project history and details', () => {
    const projectHistoryService = stubProjectHistoryService();
    const globalWindow = stubGlobalWindow();
    wrap({ projectHistoryService, globalWindow });

    component.debounceGetProjectDetails();

    const [delayedMethod, timeout] = globalWindow.setTimeout.getCall(0).args;
    expect(delayedMethod()).toEqual(component.getCurrentProjectHistory());
    expect(delayedMethod()).toEqual(component.getProjectDetails());
    expect(timeout).toBe(400);
    expect(globalWindow.clearTimeout.called).toBe(false);
  });

  it('should clear timeout', () => {
    const TIMEOUT_ID = 1;
    const projectHistoryService = stubProjectHistoryService();
    const globalWindow = stubGlobalWindow();
    globalWindow.setTimeout.returns(TIMEOUT_ID);
    wrap({ projectHistoryService, globalWindow });

    component.debounceGetProjectDetails();
    component.debounceGetProjectDetails();

    const [timeoutId] = globalWindow.clearTimeout.getCall(0).args;
    expect(timeoutId).toBe(TIMEOUT_ID);
  });

  it('should autofill project details when store is updated', () => {
    const projectStore = stubProjectStore();
    wrap({ projectStore });
    const subscription = projectStore.$subscribe.getCall(0).args[0];
    const newProject: Project = {
      folder: component.project.folder,
      baseName: 'projName',
      projectName: 'Project Name',
      packageName: 'com.project',
      serverPort: 8090,
    };
    subscription(undefined, {
      project: newProject,
    });
    expect(component.project).toEqual(newProject);
  });

  it('should not autofill project details when store is updated with empty values', () => {
    const projectStore = stubProjectStore();
    wrap({ projectStore });
    const BASE_NAME = 'oldBaseName';
    const PROJECT_NAME = 'Project name';
    const PACKAGE_NAME = 'com.project.old';
    const SERVER_PORT = 9090;
    component.project.baseName = BASE_NAME;
    component.project.projectName = PROJECT_NAME;
    component.project.packageName = PACKAGE_NAME;
    component.project.serverPort = SERVER_PORT;

    const subscription = projectStore.$subscribe.getCall(0).args[0];
    const newProject: Project = {
      folder: 'tmp/beer',
    };
    subscription(undefined, {
      project: newProject,
    });
    expect(component.project.baseName).toBe(BASE_NAME);
    expect(component.project.packageName).toBe(PACKAGE_NAME);
    expect(component.project.projectName).toBe(PROJECT_NAME);
    expect(component.project.serverPort).toBe(SERVER_PORT);
  });
});
