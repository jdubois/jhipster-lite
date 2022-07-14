import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import ProjectFolderRepository from '@/springboot/secondary/ProjectFolderRepository';

describe('ProjectFolderRepository', () => {
  it('should get folder name', async () => {
    const axiosHttpStub = stubAxiosHttp();
    axiosHttpStub.get.resolves({ data: '/tmp/jhlite/1234' });
    const projectFolderRepository = new ProjectFolderRepository(axiosHttpStub);

    const folder = await projectFolderRepository.get();

    expect(folder).toBe('/tmp/jhlite/1234');
  });
});
