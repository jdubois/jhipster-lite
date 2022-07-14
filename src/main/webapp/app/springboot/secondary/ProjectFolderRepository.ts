import { AxiosHttp } from '@/http/AxiosHttp';
import { ProjectFolderService } from '@/springboot/domain/ProjectFolderService';
import { FolderName } from '@/springboot/domain/FolderName';

export default class ProjectFolderRepository implements ProjectFolderService {
  constructor(private axiosHttp: AxiosHttp) {}

  get(): Promise<FolderName> {
    return this.axiosHttp.get<FolderName>('api/project-folders').then(res => res.data);
  }
}
