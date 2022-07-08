import { SetupService } from '@/springboot/domain/SetupService';
import { AxiosHttp } from '@/http/AxiosHttp';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { Project } from '@/springboot/domain/Project';

export default class SetupRepository implements SetupService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}
  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    await this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }
  async addGithubActions(project: Project): Promise<void> {
    await this.postAndGetHistory('api/developer-tools/github-actions', toRestProject(project));
  }
}
