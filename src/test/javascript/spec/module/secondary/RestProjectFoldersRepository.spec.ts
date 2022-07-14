import { stubAxiosHttp } from '../../http/AxiosHttpStub';
import { RestProjectFoldersRepository } from '@/module/secondary/RestProjectFoldersRepository';

describe('Rest project folders repository', () => {
  it('Should get project folder using axios', async () => {
    const axiosInstance = stubAxiosHttp();
    const repository = new RestProjectFoldersRepository(axiosInstance);
    axiosInstance.get.resolves({ data: '/tmp/jhlite/1234' });

    const projectFolder = await repository.get();

    expect(projectFolder).toEqual('/tmp/jhlite/1234');
  });
});
