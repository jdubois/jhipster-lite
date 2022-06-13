import { createPinia, setActivePinia } from 'pinia';
import { useProjectStore } from '@/springboot/primary/ProjectStore';
import { Project } from '@/springboot/domain/Project';
import { createProject } from '../domain/Project.fixture';

describe('ProjectStore', () => {
  beforeEach(() => {
    setActivePinia(createPinia());
  });

  it('should have an empty proejct by default', () => {
    const projectStore = useProjectStore();

    expect(projectStore.project).toEqual<Project>({
      folder: '',
    });
  });

  it('should get project', () => {
    const projectStore = useProjectStore();

    expect(projectStore.getProject).toEqual<Project>({
      folder: '',
    });
  });

  it('should set project', () => {
    const projectStore = useProjectStore();
    const project = createProject();

    projectStore.setProject(project);

    expect(projectStore.getProject).toEqual<Project>(project);
  });
});
