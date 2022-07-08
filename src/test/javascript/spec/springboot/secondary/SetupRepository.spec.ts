import { stubProjectHistoryService } from '../../common/domain/ProjectHistoryService.fixture';
import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { Project } from '../../../../../main/webapp/app/springboot/domain/Project';
import { createProject } from '../domain/Project.fixture';
import { RestProject, toRestProject } from '../../../../../main/webapp/app/springboot/secondary/RestProject';
import SetupRepository from '../../../../../main/webapp/app/springboot/secondary/SetupRepository';

const PROJECT_FOLDER = 'folder/path';

describe('SetupRepository', () => {
  it('should add Github actions', async () => {
    const projectHistoryService = stubProjectHistoryService();
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.post.resolves();
    const setupRepository = new SetupRepository(axiosHttpStub, projectHistoryService);
    const project: Project = createProject({ folder: PROJECT_FOLDER });

    await setupRepository.addGithubActions(project);

    const expectedRestProject: RestProject = toRestProject(project);
    const [uri, payload] = axiosHttpStub.post.getCall(0).args;
    expect(uri).toBe('api/developer-tools/github-actions');
    expect(payload).toEqual<RestProject>(expectedRestProject);
    const [projectFolder] = projectHistoryService.get.getCall(0).args;
    expect(projectFolder).toBe(PROJECT_FOLDER);
  });
});
