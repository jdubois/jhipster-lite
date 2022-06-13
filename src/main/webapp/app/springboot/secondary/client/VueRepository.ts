import { Project } from '@/springboot/domain/Project';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { AxiosHttp } from '@/http/AxiosHttp';
import { VueService } from '@/springboot/domain/client/VueService';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export default class VueRepository implements VueService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}

  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    await this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }

  async add(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/vue', toRestProject(project));
  }
}
