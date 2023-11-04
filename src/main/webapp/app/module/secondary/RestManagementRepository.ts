import { AxiosHttp } from '@/http/AxiosHttp';
import { ManagementInfo } from '../domain/ManagementInfo';
import { ManagementRepository } from '@/module/domain/ManagementRepository';

export class RestManagementRepository implements ManagementRepository {
  constructor(private axiosInstance: AxiosHttp) {}

  getInfo(): Promise<ManagementInfo> {
    return this.axiosInstance.get<ManagementInfo>('/management/info').then(res => res.data);
  }
}
