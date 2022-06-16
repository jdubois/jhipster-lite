import { Project } from '@/springboot/domain/Project';
import { RestProject, toRestProject } from '@/springboot/secondary/RestProject';
import { AxiosHttp } from '@/http/AxiosHttp';
import { AngularService } from '@/springboot/domain/client/AngularService';
import { ProjectHistoryService } from '@/common/domain/ProjectHistoryService';

export default class AngularRepository implements AngularService {
  constructor(private axiosHttp: AxiosHttp, private projectHistoryService: ProjectHistoryService) {}

  private async postAndGetHistory(url: string, restProject: RestProject): Promise<void> {
    await this.axiosHttp.post(url, restProject).then(() => this.projectHistoryService.get(restProject.folder));
  }

  async add(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/angular', toRestProject(project));
  }

  async addWithJWT(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/angular/jwt', toRestProject(project));
  }

  async addOauth2(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/angular/oauth2', toRestProject(project));
  }

  async addHealth(project: Project): Promise<void> {
    await this.postAndGetHistory('/api/clients/angular/admin-pages/health', toRestProject(project));
  }
}
